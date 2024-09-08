var parentElement = document.body;

parentElement.addEventListener('click', function(event) {
	if (event.target.id === 'editButton') {
		// Gestione del pulsante editButton
		var formEditProduct = document.getElementById("editProductForm");
		var isFormOpen = formEditProduct.style.display === "block";

		if (isFormOpen) {
			formEditProduct.style.display = "none";
		} else {
			formEditProduct.style.display = "block";
		}
	}
});
 
	// Gestione del pulsante addReviewButton
	var btnAddReview = document.getElementById("addReviewButton");
	var formAddReview = document.getElementById("addReview");
	var isFormOpen = false;
	
	btnAddReview.onclick = function() {
		if (isFormOpen) {
			formAddReview.style.display = "none";
	        btnAddReview.textContent = "Aggiungi recensione";
	    } else {
			formAddReview.style.display = "block";
            btnAddReview.textContent = "Chiudi";
        }
        
        isFormOpen = !isFormOpen;
    };

	// Gestione del pulsante editButton
	var btnEditProduct = document.getElementById("editButton");
	var formEditProduct = document.getElementById("editProductForm");
	var isFormOpen = false;
	
	btnEditProduct.onclick = function() {
		if (isFormOpen) {
			formEditProduct.style.display = "none";
		} else {
			formEditProduct.style.display = "block";
		}
		
		isFormOpen = !isFormOpen;
		};
		
	window.onclick = function(event) {
		if (event.target == formEditProduct) {
			formEditProduct.style.display = "none";
		}
	};
	
	var ratingLabel = document.querySelector('.rating-label');
	var ratingInput = document.querySelector('.rating');
	var clicked = false;

	ratingLabel.addEventListener('mousemove', function(e) {
		if (!clicked) {
			var rect = e.target.getBoundingClientRect();
			var offsetX = e.clientX - rect.left;
			var rating = (offsetX / rect.width) * 5;
			ratingInput.style.setProperty('--value', rating);
		}
	});
	
	 ratingLabel.addEventListener('click', function() {
        clicked = true;
    });
