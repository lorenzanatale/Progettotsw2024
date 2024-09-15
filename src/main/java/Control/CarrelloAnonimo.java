package Control;

import Model.prodottoCarrello.prodottoCarrelloBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarrelloAnonimo {
    private ArrayList<prodottoCarrelloBean> listaCarrello;

    public CarrelloAnonimo() {
        listaCarrello = new ArrayList<>();
    }

    public void addProduct(prodottoCarrelloBean prodottoCarrello) {
            if (prodottoCarrello != null) {
                listaCarrello.add(prodottoCarrello);
            } else {
                System.err.println("Warning: Null prodottoBean added to cart");
            }
        listaCarrello.add(prodottoCarrello);
        }


    public List<prodottoCarrelloBean> getListaCarrello() {
        return listaCarrello;


    }

    public void removeProduct(prodottoCarrelloBean prodottoCarrello) {
        Iterator<prodottoCarrelloBean> iterator = listaCarrello.iterator();
        while (iterator.hasNext()) {
            prodottoCarrelloBean currentProduct = iterator.next();
            if (currentProduct.getIdProdotto() == prodottoCarrello.getIdProdotto()) {
                int currentQuantity = currentProduct.getQuantita();
                int quantityToRemove = prodottoCarrello.getQuantita();
                int newQuantity = currentQuantity - quantityToRemove;

                if (newQuantity <= 0) {
                    // Rimuove l'articolo se la quantità è zero o meno
                    iterator.remove();
                } else {
                    // Altrimenti aggiorna la quantità
                    currentProduct.setQuantita(newQuantity);
                }
                return; // Esce dal metodo dopo aver trovato e gestito il prodotto
            }
        }
    }
}



