
document.getElementById('profile').addEventListener('click', function(event) {
	event.stopPropagation(); // Previene la propagazione dell'evento al document
	var dropdownMenu = document.getElementById('dropdown-menu');
	dropdownMenu.style.display = dropdownMenu.style.display === 'none' ? 'block' : 'none';
});

document.addEventListener('click', function(event) {
	var dropdownMenu = document.getElementById('dropdown-menu');
	if (dropdownMenu.style.display === 'block') {
		dropdownMenu.style.display = 'none';
	}
});
