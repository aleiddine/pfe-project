<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Espace Médecin - NoCortisol</title>
    <link rel="stylesheet" href="../global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-blue: #0077b6;
            --secondary-blue: #00b4d8;
            --light-blue: #f0f9ff;
            --white: #ffffff;
            --gray: #e9ecef;
            --shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }
        body {
            background-color: var(--light-blue);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
        }
        .header-nav {
            background: var(--white);
            padding: 15px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            margin-bottom: 40px;
        }
        .logo-text {
            font-size: 24px;
            font-weight: 700;
            color: var(--primary-blue);
        }
        .main-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .dashboard-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
        }
        @media (max-width: 900px) {
            .dashboard-grid { grid-template-columns: 1fr; }
        }
        .section-card {
            background: var(--white);
            padding: 30px;
            border-radius: 15px;
            box-shadow: var(--shadow);
            height: fit-content;
        }
        .btn-primary {
            background-color: var(--primary-blue);
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 600;
            width: 100%;
            transition: background 0.3s;
        }
        .btn-primary:hover { background-color: #023e8a; }

        .appointment-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            border-bottom: 1px solid var(--gray);
            transition: background 0.2s;
        }
        .appointment-item:hover { background-color: #f8f9fa; }
        .patient-name { font-weight: 600; color: var(--primary-blue); }
        .appointment-time { font-size: 14px; color: #666; }

        .input-group { margin-bottom: 20px; }
        .input-group label { display: block; margin-bottom: 8px; font-weight: 600; color: #444; }
        .input-group input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 6px; box-sizing: border-box; }

        .status-badge {
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 700;
        }
        .status-confirmed { background: #d4edda; color: #155724; }
    </style>
</head>
<body class="animate-fade">
    <header class="header-nav">
        <div class="logo-text"><i class="fas fa-user-md"></i>NoCortisol</div>
        <div class="user-info">
            <span>Bienvenue, <strong>${user.name}</strong></span>
            <a href="../deconnexion" style="margin-left: 20px; color: #dc3545; text-decoration: none;"><i class="fas fa-sign-out-alt"></i> Déconnexion</a>
        </div>
    </header>

    <main class="main-container">
        <h1>Tableau de Bord Médecin</h1>

        <div class="dashboard-grid">
            <!-- Gestion des Créneaux -->
            <div class="section-card">
                <h3><i class="fas fa-calendar-plus"></i> Ajouter des Disponibilités</h3>
                <form action="sessions" method="post">
                    <div class="input-group">
                        <label>Heure de début</label>
                        <input type="datetime-local" name="startTime" required>
                    </div>
                    <div class="input-group">
                        <label>Heure de fin</label>
                        <input type="datetime-local" name="endTime" required>
                    </div>
                    <button type="submit" class="btn-primary">
                        <i class="fas fa-plus"></i> Publier le créneau
                    </button>
                </form>

                <h4 style="margin-top: 30px;">Vos créneaux récents</h4>
                <div style="max-height: 300px; overflow-y: auto;">
                    <c:forEach var="slot" items="${slots}">
                        <div class="appointment-item">
                            <span class="appointment-time">${slot.startTime}</span>
                            <span class="status-badge ${slot.booked ? 'status-confirmed' : ''}">
                                ${slot.booked ? 'RÉSERVÉ' : 'LIBRE'}
                            </span>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Liste des Rendez-vous -->
            <div class="section-card">
                <h3><i class="fas fa-user-clock"></i> Rendez-vous Réservés</h3>
                <p style="color: #666; font-size: 14px; margin-bottom: 20px;">Liste des patients ayant pris rendez-vous avec vous.</p>

                <div style="max-height: 500px; overflow-y: auto;">
                    <c:forEach var="app" items="${appointments}">
                        <div class="appointment-item">
                            <div>
                                <div class="patient-name">${app.patient.name}</div>
                                <div class="appointment-time">${app.appointmentTime}</div>
                            </div>
                            <span class="status-badge status-confirmed">Confirmé</span>
                        </div>
                    </c:forEach>

                    <c:if test="${empty appointments}">
                        <div style="text-align: center; padding: 40px; color: #999;">
                            <i class="fas fa-calendar-times" style="font-size: 40px; opacity: 0.3;"></i>
                            <p>Aucun rendez-vous réservé pour le moment.</p>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
