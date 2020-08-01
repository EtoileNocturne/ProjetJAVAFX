-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  sam. 01 août 2020 à 04:13
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `projetjava`
--

-- --------------------------------------------------------

--
-- Structure de la table `classe`
--

DROP TABLE IF EXISTS `classe`;
CREATE TABLE IF NOT EXISTS `classe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) COLLATE utf8_bin NOT NULL,
  `capacite` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `classe`
--

INSERT INTO `classe` (`id`, `nom`, `capacite`) VALUES
(10, 'IUT', 150),
(11, 'IUT', 150),
(7, 'cp', 50),
(8, 'cp', 50),
(9, 'cp', 50);

-- --------------------------------------------------------

--
-- Structure de la table `eleve`
--

DROP TABLE IF EXISTS `eleve`;
CREATE TABLE IF NOT EXISTS `eleve` (
  `ideleve` bigint(20) NOT NULL AUTO_INCREMENT,
  `formation` varchar(50) COLLATE utf8_bin NOT NULL,
  `idClasse` int(11) NOT NULL,
  `idUti` int(11) NOT NULL,
  PRIMARY KEY (`ideleve`),
  KEY `idUti` (`idUti`),
  KEY `idClasse` (`idClasse`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `eleve`
--

INSERT INTO `eleve` (`ideleve`, `formation`, `idClasse`, `idUti`) VALUES
(15, 'true', 1, 16),
(14, 'ril', 1, 15),
(13, 'ril', 1, 14),
(12, 'ril', 1, 13),
(11, 'ril', 1, 12),
(16, 'false', 1, 17),
(17, 'false', 1, 17),
(18, 'false', 1, 14),
(19, 'true', 1, 18),
(20, 'true', 1, 19),
(21, 'true', 1, 20);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) COLLATE utf8_bin NOT NULL,
  `prenom` varchar(50) COLLATE utf8_bin NOT NULL,
  `dateNaissance` date DEFAULT NULL,
  `telephone` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `adresse` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `ville` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin NOT NULL,
  `mdp` varchar(500) COLLATE utf8_bin NOT NULL,
  `estAdmin` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `contrainte_email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `dateNaissance`, `telephone`, `adresse`, `ville`, `email`, `mdp`, `estAdmin`) VALUES
(7, 'yildiz', 'tarik', '1997-11-18', '0778213572', '', '', 'tarik', '88e586bab89ddff842b77b06da49bc1b7b9adb1f05034a19a91834d93b1a129d', 1),
(19, 'daniel', 'mehdi', '1997-01-17', '077821', '11 imp', 'le mans', 'tarikaa', '76cbb6bf97e99b9774b84a35b2e9cd8f5a05c5c0502959515dfc85cc96c1d0f5', 0),
(20, 'aa', 'aa', '1990-01-01', '1023', 'aaa', 'aaa', 'aaa', '9834876dcfb05cb167a5c24953eba58c4ac89b1adf57f28f2f9d09af107ee8f0', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
