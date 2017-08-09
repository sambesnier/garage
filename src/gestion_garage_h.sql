-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mer. 09 août 2017 à 11:33
-- Version du serveur :  5.7.18-log
-- Version de PHP :  7.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `gestion_garage_h`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresses`
--

CREATE TABLE `adresses` (
  `id_adresse` int(10) UNSIGNED NOT NULL,
  `num_voie` int(11) NOT NULL,
  `voie` varchar(100) NOT NULL,
  `code_postal` int(11) NOT NULL,
  `ville` varchar(45) NOT NULL,
  `client` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `adresses`
--

INSERT INTO `adresses` (`id_adresse`, `num_voie`, `voie`, `code_postal`, `ville`, `client`) VALUES
(2, 16, 'avenue Pierre Mendes France', 59300, 'Lille', 2);

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

CREATE TABLE `clients` (
  `id_client` int(10) UNSIGNED NOT NULL,
  `nom` varchar(45) NOT NULL,
  `prenom` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `adresse_id` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `clients`
--

INSERT INTO `clients` (`id_client`, `nom`, `prenom`, `email`, `adresse_id`) VALUES
(2, 'Besnier', 'Samuel', 'samm2i@gmail.com', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `factures`
--

CREATE TABLE `factures` (
  `id_facture` int(10) UNSIGNED NOT NULL,
  `status` tinyint(4) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `client_id` int(10) UNSIGNED NOT NULL,
  `voiture_id` int(10) UNSIGNED NOT NULL,
  `rdv_id` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `marques`
--

CREATE TABLE `marques` (
  `id_marque` int(10) UNSIGNED NOT NULL,
  `marque` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `marques`
--

INSERT INTO `marques` (`id_marque`, `marque`) VALUES
(1, 'Peugeot'),
(2, 'Renault'),
(3, 'BMW'),
(4, 'Audi');

-- --------------------------------------------------------

--
-- Structure de la table `prestations`
--

CREATE TABLE `prestations` (
  `id_prestation` int(10) UNSIGNED NOT NULL,
  `prestation` varchar(45) NOT NULL,
  `montant` decimal(10,2) NOT NULL,
  `duree` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `prestations`
--

INSERT INTO `prestations` (`id_prestation`, `prestation`, `montant`, `duree`) VALUES
(1, 'Entretien', '180.00', 60),
(2, 'Vidange', '110.00', 45);

-- --------------------------------------------------------

--
-- Structure de la table `prestation_facture`
--

CREATE TABLE `prestation_facture` (
  `prestation_id` int(10) UNSIGNED NOT NULL,
  `facture_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `rdvs`
--

CREATE TABLE `rdvs` (
  `id_rdv` int(10) UNSIGNED NOT NULL,
  `facture_id` int(10) UNSIGNED NOT NULL,
  `heure` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `voitures`
--

CREATE TABLE `voitures` (
  `id_voiture` int(10) UNSIGNED NOT NULL,
  `marque_id` int(10) UNSIGNED NOT NULL,
  `modele` varchar(45) NOT NULL,
  `puissance` int(11) NOT NULL,
  `couleur` varchar(20) NOT NULL,
  `immatriculation` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `voitures`
--

INSERT INTO `voitures` (`id_voiture`, `marque_id`, `modele`, `puissance`, `couleur`, `immatriculation`) VALUES
(1, 1, '308', 5, 'Bleue', 'AZ-194-QJ'),
(5, 3, '118D', 7, 'Noire', 'SD-154-BG');

-- --------------------------------------------------------

--
-- Structure de la table `voiture_proprietaire`
--

CREATE TABLE `voiture_proprietaire` (
  `client_id` int(10) UNSIGNED NOT NULL,
  `voiture_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `voiture_proprietaire`
--

INSERT INTO `voiture_proprietaire` (`client_id`, `voiture_id`) VALUES
(2, 1),
(2, 5);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `adresses`
--
ALTER TABLE `adresses`
  ADD PRIMARY KEY (`id_adresse`);

--
-- Index pour la table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id_client`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `client_adresse` (`adresse_id`);

--
-- Index pour la table `factures`
--
ALTER TABLE `factures`
  ADD PRIMARY KEY (`id_facture`),
  ADD KEY `client` (`client_id`),
  ADD KEY `vehicule` (`voiture_id`),
  ADD KEY `rdv` (`rdv_id`);

--
-- Index pour la table `marques`
--
ALTER TABLE `marques`
  ADD PRIMARY KEY (`id_marque`);

--
-- Index pour la table `prestations`
--
ALTER TABLE `prestations`
  ADD PRIMARY KEY (`id_prestation`);

--
-- Index pour la table `prestation_facture`
--
ALTER TABLE `prestation_facture`
  ADD KEY `facture` (`facture_id`),
  ADD KEY `prestation` (`prestation_id`);

--
-- Index pour la table `rdvs`
--
ALTER TABLE `rdvs`
  ADD PRIMARY KEY (`id_rdv`),
  ADD KEY `facture_id` (`facture_id`);

--
-- Index pour la table `voitures`
--
ALTER TABLE `voitures`
  ADD PRIMARY KEY (`id_voiture`),
  ADD KEY `voiture_marque` (`marque_id`);

--
-- Index pour la table `voiture_proprietaire`
--
ALTER TABLE `voiture_proprietaire`
  ADD KEY `voiture` (`voiture_id`),
  ADD KEY `proprietaire` (`client_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `adresses`
--
ALTER TABLE `adresses`
  MODIFY `id_adresse` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `clients`
--
ALTER TABLE `clients`
  MODIFY `id_client` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `factures`
--
ALTER TABLE `factures`
  MODIFY `id_facture` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `marques`
--
ALTER TABLE `marques`
  MODIFY `id_marque` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `prestations`
--
ALTER TABLE `prestations`
  MODIFY `id_prestation` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `rdvs`
--
ALTER TABLE `rdvs`
  MODIFY `id_rdv` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `voitures`
--
ALTER TABLE `voitures`
  MODIFY `id_voiture` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `clients`
--
ALTER TABLE `clients`
  ADD CONSTRAINT `client_adresse` FOREIGN KEY (`adresse_id`) REFERENCES `adresses` (`id_adresse`);

--
-- Contraintes pour la table `factures`
--
ALTER TABLE `factures`
  ADD CONSTRAINT `client` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id_client`),
  ADD CONSTRAINT `rdv` FOREIGN KEY (`rdv_id`) REFERENCES `rdvs` (`id_rdv`),
  ADD CONSTRAINT `vehicule` FOREIGN KEY (`voiture_id`) REFERENCES `voitures` (`id_voiture`);

--
-- Contraintes pour la table `prestation_facture`
--
ALTER TABLE `prestation_facture`
  ADD CONSTRAINT `facture` FOREIGN KEY (`facture_id`) REFERENCES `factures` (`id_facture`),
  ADD CONSTRAINT `prestation` FOREIGN KEY (`prestation_id`) REFERENCES `prestations` (`id_prestation`);

--
-- Contraintes pour la table `rdvs`
--
ALTER TABLE `rdvs`
  ADD CONSTRAINT `facture_id` FOREIGN KEY (`facture_id`) REFERENCES `factures` (`id_facture`);

--
-- Contraintes pour la table `voitures`
--
ALTER TABLE `voitures`
  ADD CONSTRAINT `voiture_marque` FOREIGN KEY (`marque_id`) REFERENCES `marques` (`id_marque`);

--
-- Contraintes pour la table `voiture_proprietaire`
--
ALTER TABLE `voiture_proprietaire`
  ADD CONSTRAINT `proprietaire` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id_client`),
  ADD CONSTRAINT `voiture` FOREIGN KEY (`voiture_id`) REFERENCES `voitures` (`id_voiture`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
