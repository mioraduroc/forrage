<?php
$idDemande = isset($_GET['idDemande']) ? intval($_GET['idDemande']) : 0;

$alertes = [];
$erreurCommunication = false;

if ($idDemande > 0) {
    $url = "http://localhost:8080/api/alertes/demande/" . $idDemande;

    $options = [
        "http" => [
            "method" => "GET",
            "header" => "X-Internal-Secret: SecretForage2026!\r\n"
        ]
    ];

    $context = stream_context_create($options);
    
    // Le serveur PHP appelle le serveur Java en arrière-plan
    $jsonResponse = @file_get_contents($url, false, $context);

    if ($jsonResponse !== FALSE) {
        // Décodage du JSON reçu en tableau PHP
        $alertes = json_decode($jsonResponse, true);
    } else {
        $erreurCommunication = true;
    }
}
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Alertes</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f8f9fa; color: #333; }
        .container { max-width: 600px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.05); }
        .alert-item { padding: 12px; background: #f8d7da; color: #721c24; border-left: 5px solid #dc3545; margin-bottom: 10px; border-radius: 4px; }
        .no-alert { padding: 12px; background: #d4edda; color: #155724; border-radius: 4px; }
        .error { padding: 12px; background: #fff3cd; color: #856404; border-radius: 4px; }
    </style>
</head>
<body>

<div class="container">
    <h1>Liste des alertes de la demande : <?php echo $idDemande; ?></h1>
    <hr>

    <?php if ($idDemande <= 0): ?>
        <p class="error">Aucun identifiant de demande valide n'a été fourni.</p>
        
    <?php elseif ($erreurCommunication): ?>
        <p class="error">⚠️ Impossible de communiquer avec le service Java. Vérifie que Spring Boot est bien démarré.</p>
        
    <?php elseif (!empty($alertes)): ?>
        <?php foreach ($alertes as $msg): ?>
            <div class="alert-item">
                <strong>Alerte :</strong> <?php echo htmlspecialchars($msg); ?>
            </div>
        <?php endforeach; ?>
        
    <?php else: ?>
        <div class="no-alert">
            ✅ Aucune alerte détectée pour cette demande. Les délais sont respectés !
        </div>
    <?php endif; ?>
</div>

</body>
</html>