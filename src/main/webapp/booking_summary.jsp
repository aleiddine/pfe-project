<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Confirmation de Réservation - NoCortisol</title>
    <link rel="stylesheet" href="global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .summary-card {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 80px auto;
            text-align: center;
        }
        .success-icon {
            font-size: 60px;
            color: #28a745;
            margin-bottom: 20px;
        }
        .info-box {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            text-align: left;
            margin-top: 30px;
        }
        .alert-message {
            background: #fff3cd;
            color: #856404;
            padding: 15px;
            border-radius: 8px;
            margin-top: 20px;
            font-weight: bold;
            border: 1px solid #ffeeba;
        }
    </style>
</head>
<body style="background: #f0f9ff;">
    <div class="summary-card animate-fade">
        <i class="fas fa-check-circle success-icon"></i>
        <h2 style="color: #333;">Inscription Validée !</h2>
        <p style="color: #666;">Votre inscription à la session d'examen a été enregistrée avec succès.</p>

        <div class="alert-message">
            <i class="fas fa-info-circle"></i> votre rendez-vous est apres 1h
        </div>

        <div class="info-box">
            <p><strong><i class="fas fa-user-md"></i> Médecin :</strong> ${appointment.doctor.name}</p>
            <p><strong><i class="fas fa-stethoscope"></i> Spécialité :</strong> ${appointment.doctor.specialty}</p>
            <p><strong><i class="fas fa-clock"></i> Heure de passage prévue :</strong> ${appointment.appointmentTime}</p>
            <p><strong><i class="fas fa-map-marker-alt"></i> Lieu :</strong> ${appointment.doctor.location}</p>
        </div>

        <div style="margin-top: 40px; display: flex; gap: 15px; justify-content: center;">
            <a href="rechercheMedecins" class="btn btn-primary" style="background: #0077b6; color: white; padding: 12px 25px; border-radius: 8px; text-decoration: none; font-weight: 600;">
                <i class="fas fa-search"></i> Retour à la recherche
            </a>
            <a href="patient/rendezvous" style="color: #0077b6; text-decoration: none; font-weight: 600; padding: 12px 25px;">
                Mes rendez-vous
            </a>
        </div>
    </div>
</body>
</html>
