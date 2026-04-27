<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Espace Patient - NoCortisol</title>
    <link rel="stylesheet" href="global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-blue: #0077b6;
            --secondary-blue: #00b4d8;
            --light-blue: #f0f9ff;
            --white: #ffffff;
            --shadow: 0 4px 15px rgba(0,0,0,0.08);
            --border-radius: 12px;
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
        .search-container {
            background: var(--white);
            padding: 35px;
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
            margin-bottom: 40px;
        }
        .doctor-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 25px;
        }
        .doctor-card {
            background: var(--white);
            border-radius: var(--border-radius);
            padding: 25px;
            box-shadow: var(--shadow);
            text-align: center;
            transition: transform 0.3s ease;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .doctor-card:hover {
            transform: translateY(-5px);
        }
        .avatar-circle {
            width: 70px;
            height: 70px;
            background: var(--light-blue);
            color: var(--primary-blue);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            margin: 0 auto 15px;
        }
        .specialty-label {
            background: var(--light-blue);
            color: var(--primary-blue);
            padding: 4px 12px;
            border-radius: 15px;
            font-size: 13px;
            font-weight: 600;
            display: inline-block;
            margin-bottom: 10px;
        }
        .location-tag {
            font-size: 14px;
            color: #666;
            margin-bottom: 20px;
        }
        .btn-appointments {
            background: var(--secondary-blue);
            color: white;
            padding: 10px 20px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            transition: opacity 0.3s;
        }
        .btn-appointments:hover { opacity: 0.8; }
    </style>
</head>
<body class="animate-fade">
    <header class="header-nav">
        <div class="logo-text"><i class="fas fa-heartbeat"></i> NoCortisol</div>
        <div class="nav-links">
            <a href="patient/rendezvous" class="btn-appointments"><i class="fas fa-calendar-alt"></i> Mes Rendez-vous</a>
            <span style="margin-left: 20px;">Utilisateur: <strong>${user.name}</strong></span>
            <a href="deconnexion" style="margin-left: 20px; color: #dc3545; text-decoration: none;"><i class="fas fa-sign-out-alt"></i> Quitter</a>
        </div>
    </header>

    <main class="main-container" style="max-width: 1200px; margin: 0 auto; padding: 0 20px;">
        <h1 style="color: #333; margin-bottom: 30px;">Rechercher un Spécialiste</h1>
        
        <div class="search-container">
            <form action="rechercheMedecins" method="get" style="display: grid; grid-template-columns: 1.5fr 1fr 1fr auto; gap: 15px; align-items: flex-end;">
                <div class="input-group" style="margin: 0;">
                    <label style="display: block; margin-bottom: 8px; font-weight: 600;">Nom ou mot-clé</label>
                    <input type="text" name="query" placeholder="Ex: Dr. Ahmed..." style="width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px;">
                </div>
                <div class="input-group" style="margin: 0;">
                    <label style="display: block; margin-bottom: 8px; font-weight: 600;">Localisation</label>
                    <input type="text" name="location" placeholder="Ex: tunis-ariana..." style="width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px;">
                </div>
                <div class="input-group" style="margin: 0;">
                    <label style="display: block; margin-bottom: 8px; font-weight: 600;">Spécialité</label>
                    <select name="specialty" style="width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px;">
                        <option value="all">Toutes</option>
                        <option value="Cardiologie">Cardiologie</option>
                        <option value="Pédiatrie">Pédiatrie</option>
                        <option value="Médecine Générale">Médecine Générale</option>
                        <option value="Dermatologie">Dermatologie</option>
                        <option value="Orthopédie">Orthopédie</option>
                        <option value="Neurologie">Neurologie</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary" style="background: var(--primary-blue); color: white; border: none; padding: 12px 30px; border-radius: 8px; cursor: pointer; font-weight: 600;">
                    <i class="fas fa-search"></i> Rechercher
                </button>
            </form>
        </div>

        <div class="doctor-grid">
            <c:forEach var="doctor" items="${doctors}">
                <div class="doctor-card">
                    <div>
                        <div class="avatar-circle">
                            <i class="fas fa-user-md"></i>
                        </div>
                        <h3 style="margin: 0 0 5px 0;">${doctor.name}</h3>
                        <span class="specialty-label">${doctor.specialty}</span>
                        <div class="location-tag">
                            <i class="fas fa-map-marker-alt" style="color: var(--secondary-blue);"></i> 
                            ${not empty doctor.location ? doctor.location : 'Non spécifiée'}
                        </div>
                    </div>
                    <button class="btn btn-outline" style="width: 100%; padding: 12px; background: white; border: 2px solid var(--primary-blue); color: var(--primary-blue); border-radius: 8px; cursor: pointer; font-weight: 600;" 
                            onclick="window.location.href='voirSessions?id=${doctor.id}'">
                        <i class="far fa-calendar-check"></i> Réserver rendez-vous
                    </button>
                </div>
            </c:forEach>
            
            <c:if test="${empty doctors}">
                <div style="grid-column: 1 / -1; text-align: center; padding: 60px; color: #666;">
                    <i class="fas fa-user-slash" style="font-size: 50px; margin-bottom: 20px; opacity: 0.3;"></i>
                    <h3>Aucun médecin trouvé</h3>
                    <p>Essayez de modifier vos critères de recherche (Nom, Spécialité ou Localisation).</p>
                </div>
            </c:if>
        </div>
    </main>
</body>
</html>
