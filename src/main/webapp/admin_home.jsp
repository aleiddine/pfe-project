<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Administration - NoCortisol</title>
    <link rel="stylesheet" href="../global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .admin-sidebar {
            width: 250px;
            background: #2c3e50;
            color: white;
            height: 100vh;
            position: fixed;
            padding: 20px;
        }
        .main-content {
            margin-left: 270px;
            padding: 40px;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-bottom: 40px;
        }
        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            text-align: center;
        }
        .data-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
        }
        .data-table th, .data-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        .data-table th { background: #f8f9fa; color: #555; }
        .btn-delete {
            color: #dc3545;
            background: none;
            border: none;
            cursor: pointer;
            font-size: 18px;
        }
        .btn-delete:hover { color: #8b0000; }
    </style>
</head>
<body style="background: #f4f7f6; margin: 0;">
    <div class="admin-sidebar">
        <h2 style="color: #3498db;"><i class="fas fa-shield-alt"></i> Admin Panel</h2>
        <hr style="border: 0.5px solid #444; margin: 20px 0;">
        <nav>
            <p style="font-size: 14px; color: #888;">GÉNERAL</p>
            <ul style="list-style: none; padding: 0;">
                <li style="padding: 10px 0;"><i class="fas fa-tachometer-alt"></i> Dashboard</li>
            </ul>
        </nav>
        <div style="position: absolute; bottom: 20px;">
            <a href="../deconnexion" style="color: #e74c3c; text-decoration: none;"><i class="fas fa-sign-out-alt"></i> Déconnexion</a>
        </div>
    </div>

    <div class="main-content">
        <h1>Bienvenue, Administrateur</h1>
        
        <div class="stats-grid">
            <div class="stat-card">
                <i class="fas fa-user-md" style="font-size: 30px; color: #3498db;"></i>
                <h2 style="margin: 10px 0;">${doctorCount}</h2>
                <p style="color: #888; font-size: 14px;">Médecins Inscrits</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-users" style="font-size: 30px; color: #2ecc71;"></i>
                <h2 style="margin: 10px 0;">${patientCount}</h2>
                <p style="color: #888; font-size: 14px;">Patients Inscrits</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-calendar-alt" style="font-size: 30px; color: #f1c40f;"></i>
                <h2 style="margin: 10px 0;">${appointmentCount}</h2>
                <p style="color: #888; font-size: 14px;">Total Rendez-vous</p>
            </div>
        </div>

        <section style="margin-bottom: 40px;">
            <h3><i class="fas fa-user-md"></i> Liste des Médecins</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Spécialité</th>
                        <th>Localisation</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="doc" items="${doctors}">
                        <tr>
                            <td><strong>${doc.name}</strong></td>
                            <td>${doc.specialty}</td>
                            <td>${doc.location}</td>
                            <td>${doc.email}</td>
                            <td>
                                <form action="tableauDeBord" method="post" onsubmit="return confirm('Supprimer ce médecin ?');" style="display: inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${doc.id}">
                                    <input type="hidden" name="type" value="doctor">
                                    <button type="submit" class="btn-delete"><i class="fas fa-trash-alt"></i></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty doctors}">
                        <tr><td colspan="5" style="text-align: center; color: #999;">Aucun médecin enregistré</td></tr>
                    </c:if>
                </tbody>
            </table>
        </section>

        <section style="margin-bottom: 40px;">
            <h3><i class="fas fa-users"></i> Liste des Patients</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${patients}">
                        <tr>
                            <td><strong>${p.name}</strong></td>
                            <td>${p.email}</td>
                            <td>
                                <form action="tableauDeBord" method="post" onsubmit="return confirm('Supprimer ce patient ?');" style="display: inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${p.id}">
                                    <input type="hidden" name="type" value="patient">
                                    <button type="submit" class="btn-delete"><i class="fas fa-trash-alt"></i></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty patients}">
                        <tr><td colspan="3" style="text-align: center; color: #999;">Aucun patient enregistré</td></tr>
                    </c:if>
                </tbody>
            </table>
        </section>

        <section>
            <h3><i class="fas fa-calendar-check"></i> Liste des Rendez-vous</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Patient</th>
                        <th>Médecin</th>
                        <th>Date & Heure</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="app" items="${appointments}">
                        <tr>
                            <td>${app.patient.name}</td>
                            <td>Dr. ${app.doctor.name}</td>
                            <td>${app.appointmentTime}</td>
                            <td>
                                <span style="background: #e8f5e9; color: #2e7d32; padding: 4px 8px; border-radius: 5px; font-size: 12px; font-weight: bold;">
                                    ${app.status == 'CONFIRMED' ? 'CONFIRMÉ' : app.status}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty appointments}">
                        <tr><td colspan="4" style="text-align: center; color: #999;">Aucun rendez-vous enregistré</td></tr>
                    </c:if>
                </tbody>
            </table>
        </section>
    </div>
</body>
</html>
