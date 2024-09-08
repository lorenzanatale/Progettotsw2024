use ecommercestrumenti;

INSERT INTO Utente (username, email, isAdmin, password) VALUES
('mario_rossi', 'mario.rossi@example.com', 0, SHA2('password123', 512)),
('giulia_admin', 'giulia@example.com', 1, SHA2('adminpassword', 512)),
('luigi_verdi', 'verdi@example.com', 0, SHA2('password456', 512));

INSERT INTO InfoConsegna (citta, cap, via, numcivico, destinatario, idUtente, isDefault) VALUES
('Milano', 20100, 'Via Roma', 10, 'Mario Rossi', 1, 1),
('Roma', 00100, 'Via Milano', 21, 'Giulia Admin', 2, 1),
('Napoli', 80100, 'Via Napoli', 3, 'Luigi Verdi', 3, 1);

INSERT INTO Ordine (idUtente, infoConsegna) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO Prodotto (nome, descrizione, categoria, prezzo, IVA, visibile, disponibilità, imgPath) VALUES
('Chitarra Acustica', 'Chitarra acustica in legno', 'Strumenti', 99.99, '22', 1, 50, 'img/prodotti/chitarra.jpg'),
('Batteria Elettronica', 'Batteria elettronica con suoni campionati', 'Strumenti', 749.99, '22', 1, 15, 'img/prodotti/batteria.jpg'),
('Violino', 'Violino in legno massello con finitura lucida', 'Strumenti', 349.99, '22', 1, 30, 'img/prodotti/violino.jpg'),
('Saxofono', 'Saxofono in ottone con custodia inclusa', 'Strumenti', 499.99, '22', 1, 25, 'img/prodotti/saxofono.jpg'),
('Basso Elettrico', 'Basso elettrico a 4 corde con equalizzatore attivo', 'Strumenti', 599.99, '22', 1, 30, 'img/prodotti/basso.jpg'),
('Microfono USB', 'Microfono USB professionale', 'Attrezzatura', 149.99, '22', 1, 100, 'img/prodotti/microfono.jpg'),
('Amplificatore per Chitarra', 'Amplificatore per chitarra da 50W con effetti integrati', 'Attrezzatura', 299.99, '22', 1, 25, 'img/prodotti/amplificatore.jpg'),
('Cuffie da Studio', 'Cuffie da studio professionali con isolamento acustico', 'Attrezzatura', 129.99, '22', 1, 75, 'img/prodotti/cuffie.jpg'),
('Cavo Jack 6.3mm', 'Cavo per strumenti musicali da 6.3mm, lungo 3 metri', 'Attrezzatura', 19.99, '22', 1, 200, 'img/prodotti/cavo.jpg'),
('Plettro in Nylon', 'Set di 12 plettri in nylon di diverse misure', 'Attrezzatura', 9.99, '22', 1, 500, 'img/prodotti/plettro.jpg'),
('Software di Editing Video', 'Software avanzato per l\'editing video con supporto 4K e effetti speciali', 'Software', 299.99, '22', 1, 100, 'img/prodotti/softwarepr.png'),
('Software Produzione Audio', 'Software per la produzione musicale', 'Software', 199.99, '22', 1, 200, 'img/prodotti/softwaregb.png');

INSERT INTO Carrello (IdUtente) VALUES
(1),
(2),
(3);

INSERT INTO ProdottoOrdine (nome, idProdotto, IdOrdine, quantità, prezzo, IVA) VALUES
('Chitarra Acustica', 1, 1, 1, 99.99, '22'),
('Microfono USB', 6, 2, 2, 149.99, '22'),
('Software Produzione Audio', 12, 3, 1, 199.99, '22'),
('Cuffie da Studio', 8, 1, 2, 129.99, '22'),
('Amplificatore per Chitarra', 7, 2, 1, 299.99, '22'),
('Batteria Elettronica', 2, 3, 1, 749.99, '22');

INSERT INTO ProdottoCarrello (idProdotto, IdCarrello, quantita, nome) VALUES
(11, 1, 1, "Software di Editing Video"),
(1, 2, 1, "Chitarra Acustica"),
(10, 2, 3, "Plettro in Nylon"),
(5, 3, 2, "Basso Elettrico"),
(9, 3, 1, "Cavo Jack 6.3mm");

INSERT INTO Recensione (idUtente, titolo, commento, valutazione, data, idProdotto) VALUES
(1, 'Ottima chitarra', 'La qualità del suono è incredibile.', 4.5, '2023-08-10', 1),
(2, 'Microfono perfetto', 'Audio cristallino e senza interferenze.', 5.0, '2023-08-11', 6),
(3, 'Software utile', 'Un software indispensabile per i produttori.', 4.0, '2023-08-12', 12);