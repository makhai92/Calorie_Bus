document.addEventListener("DOMContentLoaded", function() {
    const checkInButton = document.getElementById("check-in-button");
    const resetButton = document.getElementById("reset-button");
    const attendanceResult = document.getElementById("attendance-result");
    const message = document.getElementById("message");
    const calendarBody = document.querySelector(".calendar-body tbody");
    const calendarTitle = document.getElementById("calendar-title");
    const prevMonthButton = document.getElementById("prev-month");
    const nextMonthButton = document.getElementById("next-month");
    const prevYearButton = document.getElementById("prev-year");
    const nextYearButton = document.getElementById("next-year");
    const modal = document.getElementById("modal");
    const closeModal = document.querySelector(".close");
    const modalMessage = document.getElementById("modal-message");

    let currentYear = new Date().getFullYear();
    let currentMonth = new Date().getMonth();
    let attendanceCount = parseInt(localStorage.getItem('attendanceCount')) || 0;
    let todayDate = new Date().toISOString().split('T')[0];

    function generateCalendar(year, month) {
        calendarBody.innerHTML = ""; // Clear the existing calendar
        const firstDay = new Date(year, month, 1).getDay();
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        let date = 1;

        for (let i = 0; i < 6; i++) {
            const row = document.createElement("tr");

            for (let j = 0; j < 7; j++) {
                const cell = document.createElement("td");
                if (i === 0 && j < firstDay) {
                    cell.classList.add("empty");
                } else if (date > daysInMonth) {
                    cell.classList.add("empty");
                } else {
                    const dayDiv = document.createElement("div");
                    dayDiv.classList.add("day");

                    const stampDiv = document.createElement("div");
                    stampDiv.classList.add("stamp");
                    stampDiv.dataset.date = `${year}-${String(month + 1).padStart(2, '0')}-${String(date).padStart(2, '0')}`;

                    const dateP = document.createElement("p");
                    dateP.textContent = `${date}`;

                    dayDiv.appendChild(stampDiv);
                    dayDiv.appendChild(dateP);
                    cell.appendChild(dayDiv);

                    if (localStorage.getItem(stampDiv.dataset.date) === "checked") {
                        stampDiv.classList.add("active");
                    }

                    date++;
                }
                row.appendChild(cell);
            }
            calendarBody.appendChild(row);
        }

        calendarTitle.textContent = `${year}년 ${month + 1}월`;
    }

    function updateAttendanceStatus() {
        const todayStamp = document.querySelector(`.stamp[data-date="${todayDate}"]`);

        if (localStorage.getItem(todayDate) === "checked") {
            attendanceResult.textContent = "출석 완료";
            attendanceResult.style.color = "green";
        } else {
            attendanceResult.textContent = "미출석";
            attendanceResult.style.color = "red";
        }

        message.textContent = "";

        if (attendanceCount % 10 === 0 && attendanceCount > 0) {
            modalMessage.textContent = "축하합니다! 룰렛을 돌릴 수 있는 쿠폰을 받았습니다.";
            modal.style.display = "block"; // Show modal every 10 check-ins
        }
    }

    checkInButton.addEventListener("click", function() {
        if (localStorage.getItem(todayDate) === "checked") {
            modalMessage.textContent = "오늘 이미 출석체크를 하셨습니다.";
            modal.style.display = "block"; // Show modal if already checked in today
            return;
        }

        localStorage.setItem(todayDate, "checked");

        attendanceCount++;
        localStorage.setItem('attendanceCount', attendanceCount);

        updateAttendanceStatus();

        const todayStamp = document.querySelector(`.stamp[data-date="${todayDate}"]`);
        if (todayStamp) {
            todayStamp.classList.add("active");
        }
    });
	/*
    resetButton.addEventListener("click", function() {
        localStorage.clear();
        attendanceCount = 0;
        todayDate = new Date().toISOString().split('T')[0];
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
        modalMessage.textContent = "출석 데이터가 리셋되었습니다.";
        modal.style.display = "block"; // Show modal after reset
    });
	*/
    prevMonthButton.addEventListener("click", function() {
        if (currentMonth === 0) {
            currentMonth = 11;
            currentYear--;
        } else {
            currentMonth--;
        }
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
    });

    nextMonthButton.addEventListener("click", function() {
        if (currentMonth === 11) {
            currentMonth = 0;
            currentYear++;
        } else {
            currentMonth++;
        }
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
    });

    prevYearButton.addEventListener("click", function() {
        currentYear--;
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
    });

    nextYearButton.addEventListener("click", function() {
        currentYear++;
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
    });

    closeModal.addEventListener("click", function() {
        modal.style.display = "none";
    });

    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });

    generateCalendar(currentYear, currentMonth);
    updateAttendanceStatus();
});












/*document.addEventListener("DOMContentLoaded", function() {
    const checkInButton = document.getElementById("check-in-button");
    const resetButton = document.getElementById("reset-button");
    const attendanceResult = document.getElementById("attendance-result");
    const message = document.getElementById("message");
    const calendarBody = document.querySelector(".calendar-body tbody");
    const calendarTitle = document.getElementById("calendar-title");
    const prevMonthButton = document.getElementById("prev-month");
    const nextMonthButton = document.getElementById("next-month");
    const prevYearButton = document.getElementById("prev-year");
    const nextYearButton = document.getElementById("next-year");
    const modal = document.getElementById("modal");
    const closeModal = document.querySelector(".close");

    let currentYear = new Date().getFullYear();
    let currentMonth = new Date().getMonth();
    let attendanceCount = parseInt(localStorage.getItem('attendanceCount')) || 0;
    let nextAttendanceDate = localStorage.getItem('nextAttendanceDate') || new Date().toISOString().split('T')[0];

    function generateCalendar(year, month) {
        calendarBody.innerHTML = ""; // Clear the existing calendar
        const firstDay = new Date(year, month, 1).getDay();
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        let date = 1;

        for (let i = 0; i < 6; i++) {
            const row = document.createElement("tr");

            for (let j = 0; j < 7; j++) {
                const cell = document.createElement("td");
                if (i === 0 && j < firstDay) {
                    cell.classList.add("empty");
                } else if (date > daysInMonth) {
                    cell.classList.add("empty");
                } else {
                    const dayDiv = document.createElement("div");
                    dayDiv.classList.add("day");

                    const stampDiv = document.createElement("div");
                    stampDiv.classList.add("stamp");
                    stampDiv.dataset.date = `${year}-${String(month + 1).padStart(2, '0')}-${String(date).padStart(2, '0')}`;

                    const dateP = document.createElement("p");
                    dateP.textContent = `${date}`;

                    dayDiv.appendChild(stampDiv);
                    dayDiv.appendChild(dateP);
                    cell.appendChild(dayDiv);

                    if (localStorage.getItem(stampDiv.dataset.date) === "checked") {
                        stampDiv.classList.add("active");
                    }

                    date++;
                }
                row.appendChild(cell);
            }
            calendarBody.appendChild(row);
        }

        calendarTitle.textContent = `${year}년 ${month + 1}월`;
    }

    function updateAttendanceStatus() {
        const today = new Date().toISOString().split('T')[0];
        const todayStamp = document.querySelector(`.stamp[data-date="${today}"]`);

        if (localStorage.getItem(today) === "checked") {
            attendanceResult.textContent = "출석 완료";
            attendanceResult.style.color = "green";
        } else {
            attendanceResult.textContent = "미출석";
            attendanceResult.style.color = "red";
        }

        message.textContent = "";

        if (attendanceCount % 10 === 0 && attendanceCount > 0) {
            // 10회 출석 체크마다 모달 창을 띄움
            modal.style.display = "block";
        }
    }

    checkInButton.addEventListener("click", function() {
        const nextDate = new Date(nextAttendanceDate);
        const formattedDate = nextDate.toISOString().split('T')[0];
        localStorage.setItem(formattedDate, "checked");

        // 출석 체크 횟수 증가
        attendanceCount++;
        localStorage.setItem('attendanceCount', attendanceCount);

        updateAttendanceStatus();

        const todayStamp = document.querySelector(`.stamp[data-date="${formattedDate}"]`);
        if (todayStamp) {
            todayStamp.classList.add("active");
        }

        // 다음 출석 체크 날짜 설정
        nextDate.setDate(nextDate.getDate() + 1);
        nextAttendanceDate = nextDate.toISOString().split('T')[0];
        localStorage.setItem('nextAttendanceDate', nextAttendanceDate);
    });

    resetButton.addEventListener("click", function() {
        localStorage.clear();
        attendanceCount = 0;
        nextAttendanceDate = new Date().toISOString().split('T')[0];
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
    });

    prevMonthButton.addEventListener("click", function() {
        if (currentMonth === 0) {
            currentMonth = 11;
            currentYear--;
        } else {
            currentMonth--;
        }
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
    });

    nextMonthButton.addEventListener("click", function() {
        if (currentMonth === 11) {
            currentMonth = 0;
            currentYear++;
        } else {
            currentMonth++;
        }
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
    });

    prevYearButton.addEventListener("click", function() {
        currentYear--;
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
    });

    nextYearButton.addEventListener("click", function() {
        currentYear++;
        generateCalendar(currentYear, currentMonth);
        updateAttendanceStatus();
    });

    closeModal.addEventListener("click", function() {
        modal.style.display = "none";
    });

    window.addEventListener("click", function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    });

    generateCalendar(currentYear, currentMonth);
    updateAttendanceStatus();
});
*/