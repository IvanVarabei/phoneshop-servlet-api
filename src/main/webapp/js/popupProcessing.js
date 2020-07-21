let previousPopupId = null;

function changePopup(n) {
    if (previousPopupId !== n) {
        const current = document.getElementById(n);
        current.classList.toggle("show");
    }
    if (previousPopupId != null) {
        const previous = document.getElementById(previousPopupId);
        previous.classList.toggle("show");
    }
    previousPopupId = previousPopupId === n ? null : n;
}