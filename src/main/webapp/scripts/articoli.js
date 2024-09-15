const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');

const app = express();
const port = 3306;

app.use(bodyParser.json());

// Configura la connessione al database
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'jdbc:mysql://localhost:3306/jdbc:mysql://localhost:3306/ecommercestrumenti'
});

db.connect(err => {
    if (err) throw err;
    console.log('Connesso al database');
});

// API per ottenere gli articoli del carrello
app.get('/', (req, res) => {
    db.query('SELECT * FROM carrello', (err, results) => {
        if (err) throw err;
        res.json(results);
    });
});

// API per aggiungere un articolo
app.post('/cart', (req, res) => {
    const { name, price } = req.body;
    db.query('INSERT INTO carrello (name, price) VALUES (?, ?)', [name, price], (err, results) => {
        if (err) throw err;
        res.json({ message: 'Articolo aggiunto' });
    });
});

// API per rimuovere un articolo
app.delete('/cart/:id', (req, res) => {
    const { id } = req.params;
    db.query('DELETE FROM cart_items WHERE id = ?', [id], (err, results) => {
        if (err) throw err;
        res.json({ message: 'Articolo rimosso' });
    });
});

app.listen(port, () => {
    console.log(`Server in ascolto su http://localhost:${port}`);
});
