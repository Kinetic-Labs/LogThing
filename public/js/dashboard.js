document.addEventListener('DOMContentLoaded', async _event => {
    const logList = document.getElementById('logList');
    const logLevelFilters = document.getElementById('log-level-filters');
    const logs = await fetch('/api/logs/get').then(response => response.json());
    const logKinds = await fetch('/api/logs/levels').then(response => response.json());
    const logCounts = {};

    window.activeFilters = ['ALL'];
    logKinds.forEach(kind => logCounts[kind] = 0);
    let totalLogs = logs.length;

    logs.forEach(log => {
        const level = log.level || 'UNKNOWN';
        logCounts[level] = (logCounts[level] || 0) + 1;
    });

    logLevelFilters.innerHTML = `
        <button data-level="ALL" class="filter-button active">
            All (${totalLogs})
        </button>
    `;

    logLevelFilters.innerHTML += Object.keys(logCounts).map(level =>
        `<button data-level="${level}" class="filter-button">
            ${level} (${logCounts[level]})
        </button>`
    ).join('');

    document.querySelectorAll('.filter-button').forEach(button => {
        button.addEventListener('click', () => filterLogs(button.dataset.level));
    });

    logList.innerHTML = logs.map((log, index) => `
        <li class="log-entry" 
            data-level="${log.level || 'UNKNOWN'}" 
            style="--i: ${index}; border-left-color: ${getLevelColor(log.level || 'UNKNOWN')}">
            
            ${(log.timestamp || log.tag) ? `
            <span class="log-meta">
                ${log.timestamp ? `<span class="log-timestamp">${log.timestamp}</span>` : ''}
                ${log.tag ? `<span class="log-tag">${log.tag}</span>` : ''}
            </span>
            ` : ''}
            <span class="log-level" style="color: ${getLevelColor(log.level || 'UNKNOWN')}">
                [${log.level || 'UNKNOWN'}]
            </span>
            <span class="log-message">${log.message}</span>
        </li>
    `).join('');

    const ctx = document.getElementById('logChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: Object.keys(logCounts),
            datasets: [{
                label: 'Log Frequency',
                data: Object.values(logCounts),
                backgroundColor: Object.keys(logCounts).map(level => getLevelColor(level)),
                borderColor: Object.keys(logCounts).map(level => getLevelColor(level)),
                borderWidth: 1,
                borderRadius: 4,
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {legend: {display: false}},
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {color: '#a0a0a0', stepSize: 1},
                    grid: {color: '#333333'}
                },
                x: {
                    ticks: {color: '#a0a0a0'},
                    grid: {display: false}
                }
            }
        }
    });
});

function getLevelColor(level) {
    const colors = {
        'INFO': '#a0a8b0',
        'WARN': '#d0c8a0',
        'ERROR': '#f0d0d0',
        'DEBUG': '#a0b0a8',
        'TRACE': '#9090a0',
        'FATAL': '#f0e0e0',
        'UNKNOWN': '#808080'
    };

    return colors[level] || '#909090';
}

function filterLogs(level) {
    const filterButtons = document.querySelectorAll('.filter-button');
    const allButton = document.querySelector('.filter-button[data-level="ALL"]');

    if(level === 'ALL') {
        activeFilters = ['ALL'];
        allButton.classList.add('active');
    } else {
        if(activeFilters.includes('ALL')) {
            activeFilters = [];
            allButton.classList.remove('active');
        }

        const index = activeFilters.indexOf(level);
        if(index > -1) {
            activeFilters.splice(index, 1);
        } else {
            activeFilters.push(level);
        }

        if(activeFilters.length === 0) {
            activeFilters = ['ALL'];
            allButton.classList.add('active');
        }
    }

    const logEntries = document.querySelectorAll('.log-entry');

    logEntries.forEach(entry => {
        const isVisible = activeFilters.includes('ALL') || activeFilters.includes(entry.dataset.level);
        const wasVisible = !entry.classList.contains('filtered');

        if(wasVisible && !isVisible) {
            entry.classList.add('filtered');
            entry.style.animation = '';
        } else if(!wasVisible && isVisible) {
            entry.classList.remove('filtered');
            entry.style.animation = '';
        }
    });

    filterButtons.forEach(button => {
        button.classList.toggle('active', activeFilters.includes(button.dataset.level));
    });
}
