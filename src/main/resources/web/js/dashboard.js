document.addEventListener('DOMContentLoaded', async _event => {
    const logList = document.getElementById('logList');
    const logLevelFilters = document.getElementById('log-level-filters');
    const logs = await fetch('/api/logs/get').then(response => response.json());
    const logKinds = await fetch('/api/logs/levels').then(response => response.json());
    const logCounts = {};

    logKinds.forEach(kind => logCounts[kind] = 0);

    logs.forEach(log => {
        const level = log.level || 'UNKNOWN';

        logCounts[level] = (logCounts[level] || 0) + 1;
    });

    logLevelFilters.innerHTML += Object.keys(logCounts).map(level =>
        `<button data-secondary class="filter-button" onclick="filterLogs('${level}')">
            ${level} (${logCounts[level]})
        </button>`
    ).join('');

    logList.innerHTML = logs.map(log => `
        <li class="log-entry" data-level="${log.level || 'UNKNOWN'}" style="border-left-color: ${getLevelColor(log.level || 'UNKNOWN')}">
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
            plugins: {
                legend: {
                    display: false,
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        color: '#a0a0a0',
                        stepSize: 1
                    },
                    grid: {
                        color: '#333333'
                    }
                },
                x: {
                    ticks: {
                        color: '#a0a0a0'
                    },
                    grid: {
                        display: false
                    }
                }
            }
        }
    });
});

function getLevelColor(level) {
    const colors = {
        'INFO': '#c94aff',
        'WARNING': '#ffdd00',
        'ERROR': '#ff4444',
        'DEBUG': '#44ff85',
        'TRACE': '#00ffe1',
        'FATAL': '#ff0000',
        'UNKNOWN': '#555555'
    };

    return colors[level] || '#9e3ddc';
}

window.filterLogs = (level) => {
    const logEntries = document.querySelectorAll('.log-entry');
    const filterButtons = document.querySelectorAll('.filter-button');

    logEntries.forEach(entry =>
        entry.style.display = (level === 'ALL' || entry.dataset.level === level) ? 'flex' : 'none'
    );

    filterButtons.forEach(button => {
        if(button.textContent.startsWith(level) || (level === 'ALL' && button.textContent === 'All')) {
            button.classList.add('active');
        } else {
            button.classList.remove('active');
        }
    });
}
