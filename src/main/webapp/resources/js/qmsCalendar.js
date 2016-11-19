/*


 */
var timeout;

$(document).ready(function() {
    setAspectRatio();
});

$(window).resize(function() {
    setAspectRatio();
});

function setAspectRatio() {
    var height = window.innerHeight - $("#top").height() - $(".fc-header").height() - 35;
    var width = window.innerWidth;
    var aspectRatio = width / height;
    qmsSchedule.cfg.aspectRatio = aspectRatio;
    qmsSchedule.refresh(qmsSchedule.cfg);
    $(".fc-header").last().remove();
    $(".fc-content").last().remove();
}

PrimeFaces.locales ['en'] = {
    closeText: 'Close',
    prevText: 'Previous',
    nextText: 'Next',
    monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
    monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
    dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Tue', 'Fri', 'Sat'],
    dayNamesMin: ['S', 'M', 'T', 'W ', 'T', 'F ', 'S'],
    weekHeader: 'Week',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Only Time',
    timeText: 'Time',
    hourText: 'Time',
    minuteText: 'Minute',
    secondText: 'Second',
    currentText: 'Current Date',
    ampm: false,
    month: 'Month',
    week: 'Week',
    day: 'Day',
    allDayText: 'All Day'
};

PrimeFaces.locales['pl'] = {
    closeText: 'Zamknij',
    prevText: 'Poprzedni',
    nextText: 'Następny',
    monthNames: ['Styczeń', 'Luty', 'Marzec', 'Kwiecień', 'Maj', 'Czerwiec', 'Lipiec', 'Sierpień', 'Wrzesień', 'Październik', 'Listopad', 'Grudzień'],
    monthNamesShort: ['Sty', 'Lut', 'Mar', 'Kwi', 'Maj', 'Cze', 'Lip', 'Sie', 'Wrz', 'Paź', 'Lis', 'Gru'],
    dayNames: ['Niedziela', 'Poniedziałek', 'Wtorek', 'Środa', 'Czwartek', 'Piątek', 'Sobota'],
    dayNamesShort: ['Nie', 'Pon', 'Wt', 'Śr', 'Czw', 'Pt', 'So'],
    dayNamesMin: ['N', 'P', 'W', 'Ś', 'Cz', 'P', 'S'],
    weekHeader: 'Tydzień',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: 'r',
    timeOnlyTitle: 'Tylko czas',
    timeText: 'Czas',
    hourText: 'Godzina',
    minuteText: 'Minuta',
    secondText: 'Sekunda',
    currentText: 'Teraz',
    ampm: false,
    month: 'Miesiąc',
    week: 'Tydzień',
    day: 'Dzień',
    allDayText: 'Cały dzień'
};

