<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Inscription Session - NoCortisol</title>
    <link rel="stylesheet" href="global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .booking-card {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 50px auto;
            text-align: center;
        }
        .slot-label {
            display: flex;
            width: 100%;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            border-radius: 12px;
            border: 2px solid #eee;
            margin-bottom: 10px;
            cursor: pointer;
            transition: all 0.2s;
        }
        .slot-radio { display: none; }
        .slot-radio:checked + .slot-label {
            border-color: #0077b6;
            background: #f0f9ff;
            box-shadow: 0 4px 10px rgba(0,119,182,0.1);
        }
        .hint-text {
            font-size: 13px;
            color: #666;
            margin-top: 5px;
        }
    </style>
</head>
<body style="background: #f8fafc;">
    <div class="booking-card">
        <h2 style="color: #0077b6; margin-bottom: 10px;">Inscription à une Session</h2>
        <p style="color: #475569; margin-bottom: 30px;">
            Choisissez une session d'examen avec <strong>${doctor.name}</strong>.
            <br><small><i class="fas fa-map-marker-alt"></i> ${doctor.location}</small>
        </p>

        <form action="reserverRendezVous" method="post">
            <h4 style="text-align: left; margin-bottom: 15px; color: #1e293b;">Sessions disponibles aujourd'hui :</h4>

            <c:forEach var="slot" items="${slots}">
                <div>
                    <input type="radio" name="slotId" value="${slot.id}" id="slot_${slot.id}" class="slot-radio" required>
                    <label for="slot_${slot.id}" class="slot-label">
                        <span><i class="far fa-clock"></i> <strong>${slot.startTime} - ${slot.endTime}</strong></span>
                        <span style="color: #059669; font-size: 13px; font-weight: bold;"><i class="fas fa-unlock"></i> Ouverte</span>
                    </label>
                </div>
            </c:forEach>

            <c:if test="${empty slots}">
                <div style="background: #fff1f2; color: #e11d48; padding: 20px; border-radius: 12px; border: 1px solid #fecdd3;">
                    <i class="fas fa-exclamation-triangle"></i> Aucune session n'est ouverte pour ce médecin actuellement.
                </div>
            </c:if>

            <div style="margin-top: 35px;">
                <button type="submit" class="btn btn-primary" style="width: 100%; padding: 16px; font-size: 17px; background: #0077b6; color: white; border: none; border-radius: 10px; cursor: pointer; font-weight: 600;"
                        ${empty slots ? 'disabled' : ''}>
                    Confirmer mon inscription
                </button>
                <p class="hint-text">Note: Votre heure de passage sera estimée à 1 heure après l'ouverture de la session.</p>
            </div>
        </form>

        <div style="margin-top: 25px;">
            <a href="rechercheMedecins" style="color: #64748b; text-decoration: none; font-size: 14px;"><i class="fas fa-arrow-left"></i> Retour aux médecins</a>
        </div>
    </div>
</body>
</html>