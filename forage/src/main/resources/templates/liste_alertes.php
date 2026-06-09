<?php
$idDemande = isset($_GET['idDemande']) ? intval($_GET['idDemande']) : 0;
$alertesStr = [];
$erreurCommunication = false;

if ($idDemande > 0) {
    $url = "http://localhost:8080/api/alertes/demande/" . $idDemande; 
    $options = [
        "http" => [
            "method" => "GET"
        ]
    ];
    $context = stream_context_create($options);
    $jsonResponse = @file_get_contents($url, false, $context);

    if ($jsonResponse !== FALSE) {
        $alertesStr = json_decode($jsonResponse, true);
    } else {
        $erreurCommunication = true;
    }
}

function parserAlerte($chaine) {
    $pattern = '/Code:\s*(.*?)\s*\|\s*Demande Ref:\s*(.*?)\s*\(Client:\s*(.*?)\s*Lieu:\s*(.*?)\s*Commune:\s*(.*?)\s*District:\s*(.*?)\s*Region:\s*(.*?)\)\s*\|\s*Dates:\s*\[(.*?) -> (.*?)\]\s*\|\s*Transition:\s*\[(.*?) -> (.*?)\]\s*\|\s*Délai dépassé au statut \'(.*?)\'\s*:\s*(.*?)\s*min constatées\s*\(Max autorisé:\s*(.*?)\s*min\)/';
    
    if (preg_match($pattern, $chaine, $matches)) {
        return [
            'code'           => $matches[1],
            'reference'      => $matches[2],
            'client'         => $matches[3],
            'lieu'           => $matches[4],
            'commune'        => $matches[5],
            'district'       => $matches[6],
            'region'         => $matches[7],
            'date_precedent' => $matches[8], 
            'date_actuel'    => $matches[9], 
            'sigle_dep'      => $matches[10],
            'sigle_arr'      => $matches[11],
            'statut_nom'     => $matches[12],
            'constate'       => floatval($matches[13]),
            'maximum'        => floatval($matches[14]),
            'depassement'    => floatval($matches[13]) - floatval($matches[14])
        ];
    }
    return null;
}
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historique d'alertes</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background-color: #f9f9f9; }
        h1 { color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: left; font-size: 14px; }
        th { background-color: #f2f2f2; }
        .badge-retard { color: red; font-weight: bold; }
        .error { color: #721c24; background-color: #f8d7da; padding: 15px; border-radius: 4px; }
        .success { color: #155724; background-color: #d4edda; padding: 15px; border-radius: 4px; }
        .date-text { font-size: 12px; color: #555; }
    </style>
</head>
<body>

    <h1>Liste des alertes de la demande : <?php echo $idDemande; ?></h1>

    <?php if ($erreurCommunication): ?>
        <p class="error"> Erreur : Impossible de contacter l'API Spring Boot. Vérifie qu'elle est lancée sur le port 8080.</p>
        
    <?php elseif (!empty($alertesStr)): ?>
        <table>
            <thead>
                <tr>
                    <th>Alerte</th>
                    <th>Réf Demande</th>
                    <th>Client</th>
                    <th>Localisation</th>
                    <th>Dates Statuts</th> <th>Transition</th>
                    <th>Statut Bloqué</th>
                    <th>Temps Constaté</th>
                    <th>Temps Max</th>
                    <th>Retard</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($alertesStr as $chaineBrute): 
                    $info = parserAlerte($chaineBrute);
                    if ($info === null) continue; 
                ?>
                    <tr>
                        <td><strong><?php echo htmlspecialchars($info['code']); ?></strong></td>
                        <td><code><?php echo htmlspecialchars($info['reference']); ?></code></td>
                        <td><?php echo htmlspecialchars($info['client']); ?></td>
                        <td>
                            <small>
                                <?php echo htmlspecialchars($info['lieu']); ?>, 
                                <?php echo htmlspecialchars($info['commune']); ?>, <br>
                                <?php echo htmlspecialchars($info['district']); ?>, 
                                <?php echo htmlspecialchars($info['region']); ?>
                            </small>
                        </td>
                        <td class="date-text">
                            Début : <?php echo htmlspecialchars($info['date_precedent']); ?><br>
                            Fin : <?php echo htmlspecialchars($info['date_actuel']); ?>
                        </td>
                        <td><?php echo htmlspecialchars($info['sigle_dep']); ?> ➔ <?php echo htmlspecialchars($info['sigle_arr']); ?></td>
                        <td><?php echo htmlspecialchars($info['statut_nom']); ?></td>
                        <td><?php echo $info['constate']; ?> min</td>
                        <td><?php echo $info['maximum']; ?> min</td>
                        <td class="badge-retard">+<?php echo $info['depassement']; ?> min</td>
                    </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
        
    <?php else: ?>
        <p class="success">Aucune alerte détectée pour cette demande ! Tous les délais sont respectés.</p>
    <?php endif; ?>

</body>
</html>