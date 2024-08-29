DROP database IF exists ecommercestrumenti;
CREATE DATABASE ecommercestrumenti;
USE ecommercestrumenti;

CREATE TABLE Utente (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    isAdmin TINYINT(1) NOT NULL,
    password CHAR(128) NOT NULL,
    #SHA-512
    CONSTRAINT fk_utente_admin CHECK (isAdmin IN (0, 1))
);

CREATE TABLE InfoConsegna (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    citta VARCHAR(255) NOT NULL,
    cap INT NOT NULL,
    via VARCHAR(255) NOT NULL,
    numcivico INT NOT NULL,
    destinatario VARCHAR(255) NOT NULL,
    idUtente BIGINT UNSIGNED NOT NULL,
    isDefault TINYINT(1) DEFAULT 0 NOT NULL,
    FOREIGN KEY (idUtente) REFERENCES Utente (id)
);

CREATE TABLE Carrello (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    IdUtente BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id, IdUtente),
    FOREIGN KEY (IdUtente) REFERENCES Utente (id) ON DELETE CASCADE
);

CREATE TABLE Prodotto (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255)  NOT NULL,
    descrizione VARCHAR(4000) NOT NULL,
    categoria ENUM('Strumenti', 'Software', 'Attrezzatura'),
    prezzo DECIMAL(10, 2) NOT NULL,
    IVA ENUM('4', '10', '22') NOT NULL,
    visibile TINYINT(1) DEFAULT 1 NOT NULL,
    disponibilità INT UNSIGNED NOT NULL,
    imgPath VARCHAR(255) NOT NULL
);

CREATE TABLE Recensione (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    idUtente BIGINT UNSIGNED NOT NULL,
    titolo VARCHAR(255)  NOT NULL,
    commento VARCHAR(1024) NOT NULL,
    valutazione DECIMAL(2, 1) NOT NULL,
    data DATE NOT NULL,
    idProdotto BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id, idUtente, idProdotto, data),
    FOREIGN KEY (idUtente) REFERENCES Utente (id),
    FOREIGN KEY (idProdotto) REFERENCES Prodotto (id),
    CONSTRAINT valutazione CHECK (valutazione <= 5)
);

CREATE TABLE Ordine (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    idUtente BIGINT UNSIGNED NOT NULL,
    dataOrdine DATETIME DEFAULT CURRENT_TIMESTAMP,
    infoConsegna BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id, idUtente),
    FOREIGN KEY (idUtente) REFERENCES Utente (id),
    FOREIGN KEY (infoConsegna) REFERENCES InfoConsegna (id)
);

CREATE TABLE ProdottoOrdine (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    idProdotto BIGINT UNSIGNED NOT NULL,
    IdOrdine   BIGINT UNSIGNED NOT NULL,
    quantità INT NOT NULL,
    prezzo DECIMAL(10, 2) NOT NULL,
	IVA ENUM('4', '10', '22') NOT NULL,
    PRIMARY KEY (id, idProdotto, IdOrdine),
    FOREIGN KEY (IdOrdine) REFERENCES Ordine (id) ON DELETE CASCADE,
    FOREIGN KEY (idProdotto) REFERENCES Prodotto (id)
);

CREATE TABLE ProdottoCarrello (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    idProdotto BIGINT UNSIGNED NOT NULL,
    IdCarrello BIGINT UNSIGNED NOT NULL,
    quantita BIGINT UNSIGNED NOT NULL,
	nome VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, idProdotto, IdCarrello),
    FOREIGN KEY (IdCarrello) REFERENCES Carrello (id) ON DELETE CASCADE,
    FOREIGN KEY (idProdotto) REFERENCES Prodotto (id) ON DELETE CASCADE
);
