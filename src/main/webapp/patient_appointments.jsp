<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes Rendez-vous - NoCortisol</title>
    <link rel="stylesheet" href="../global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .appointment-card {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .status-pill {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: bold;
            background: #e8f5e9;
            color: #2e7d32;
        }
    </style>
</head>
<body style="background: #f0f9ff; font-family: sans-serif;">
    <div style="max-width: 800px; margin: 50px auto; padding: 0 20px;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 40px;">
            <h1><i class="fas fa-calendar-check" style="color: #0077b6;"></i> Mes Rendez-vous</h1>
            <a href="../rechercheMedecins" style="text-decoration: none; color: #0077b6; font-weight: 600;"><i class="fas fa-arrow-left"></i> Retour</a>
        </div>

        <c:forEach var="app" items="${appointments}">
            <div class="appointment-card animate-fade">
                <div>
                    <h3 style="margin: 0 0 10px 0;">Dr. ${app.doctor.name}</h3>
                    <p style="margin: 0; color: #666;"><i class="fas fa-stethoscope"></i> ${app.doctor.specialty}</p>
                    <p style="margin: 5px 0 0 0; color: #666;"><i class="fas fa-map-marker-alt"></i> ${app.doctor.location}</p>
                </div>
                <div style="text-align: right;">
                    <div style="font-weight: bold; font-size: 16px; margin-bottom: 10px;">${app.appointmentTime}</div>
                    <span class="status-pill">${app.status}</span>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty appointments}">
            <div style="text-align: center; padding: 60px; background: white; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.05);">
                <i class="far fa-calendar-times" style="font-size: 50px; color: #ccc; margin-bottom: 20px;"></i>
                <h3 style="color: #999;">Vous n'avez aucun rendez-vous pour le moment</h3>
                <a href="../rechercheMedecins" style="color: #0077b6; font-weight: 600; text-decoration: none;">Trouver un médecin maintenant</a>
            </div>
        </c:if>
    </div>
</body>
</html>
