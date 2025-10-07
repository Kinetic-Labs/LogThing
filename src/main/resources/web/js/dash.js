document.addEventListener('DOMContentLoaded', async() => {
    const content = document.getElementById('content');

    const logs = await fetch('/api/load', {
        method: 'GET',
    }).then(response => response.json());

    const logKinds = await fetch('/api/logkinds', {
        method: 'GET',
    }).then(response => response.json());

    const logCounts = {};
    logKinds.forEach(kind => {
        logCounts[kind] = 0;
    });

    logs.forEach(log => {
        const level = log.level || 'UNKNOWN';
        if(!logCounts[level]) {
            logCounts[level] = 0;
        }
        logCounts[level]++;
    });

    content.innerHTML = `
        <div style="width: 80%; max-width: 1200px;">
            <div style="margin-bottom: 2rem;">
                <h2>Filter by Log Level</h2>
                <div style="display: flex; gap: 10px; flex-wrap: wrap; margin-bottom: 1rem;">
                    <button data-secondary onclick="filterLogs('ALL')">All</button>
                    ${Object.keys(logCounts).map(level =>
        `<button data-secondary onclick="filterLogs('${level}')">${level}</button>`
    ).join('')}
                </div>
            </div>

            <div id="logList" style="background-color: var(--panel-background-color); padding: 20px; border-radius: 10px;">
                ${logs.map((log, _idx) => `
                    <div class="log-entry" data-level="${log.level || 'UNKNOWN'}" style="padding: 10px; margin-bottom: 10px; background-color: var(--element-dark); border-radius: 5px; border-left: 3px solid ${getLevelColor(log.level || 'UNKNOWN')};">
                        <strong style="color: ${getLevelColor(log.level || 'UNKNOWN')};">[${log.level || 'UNKNOWN'}]</strong> 
                        <span>${log.message}</span>
                    </div>
                `).join('')}
            </div>
            
            <div style="background-color: var(--panel-background-color); padding: 20px; border-radius: 10px; margin-top: 2rem;">
                <canvas id="logChart"></canvas>
            </div>
        </div>
    `;

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
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    labels: {
                        color: '#EEEEEE'
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        color: '#EEEEEE',
                        stepSize: 1
                    },
                    grid: {
                        color: '#333333'
                    }
                },
                x: {
                    ticks: {
                        color: '#EEEEEE'
                    },
                    grid: {
                        color: '#333333'
                    }
                }
            }
        }
    });
});

function getLevelColor(level) {
    const colors = {
        'INFO': '#4a9eff',
        'WARNING': '#ffa500',
        'ERROR': '#ff4444',
        'DEBUG': '#44ff44',
        'TRACE': '#888888',
        'FATAL': '#ff0000',
        'UNKNOWN': '#9e3ddc'
    };
    return colors[level] || '#9e3ddc';
}

window.filterLogs = function(level) {
    const logEntries = document.querySelectorAll('.log-entry');
    logEntries.forEach(entry => {
        if(level === 'ALL' || entry.dataset.level === level) {
            entry.style.display = 'block';
        } else {
            entry.style.display = 'none';
        }
    });
}
