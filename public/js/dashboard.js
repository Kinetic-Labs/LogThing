document.addEventListener('DOMContentLoaded', async () => {
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

    logKinds.forEach(level => {
        logLevelFilters.innerHTML += `
            <button data-level="${level}" class="filter-button">
                ${level} (${logCounts[level]})
            </button>
        `;
    });

    document.querySelectorAll('.filter-button').forEach(button => {
        button.addEventListener('click', () => filterLogs(button.dataset.level));
    });

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

    const logViewport = document.getElementById('logViewport');

    const Datasource = VScroll.makeDatasource();

    const datasource = new Datasource({
        get: (index, count, success) => {
            const filteredLogs = logs.filter(log => {
                const level = log.level || 'UNKNOWN';
                return activeFilters.includes('ALL') || activeFilters.includes(level);
            });

            const endIndex = Math.min(index + count, filteredLogs.length);
            const slice = [];

            for(let i = index; i < endIndex; i++) {
                if(filteredLogs[i]) {
                    slice.push(filteredLogs[i]);
                }
            }

            success(slice);
        }
    });

    let oldItems = [];

    const workflow = new VScroll.Workflow({
        element: logViewport,
        datasource: datasource,
        run: (newItems) => {
            if(!newItems.length && !oldItems.length) {
                return;
            }

            oldItems.forEach(item => {
                if(!newItems.find(newItem => newItem.element === item.element)) {
                    if(item.element && item.element.parentNode) {
                        item.element.parentNode.removeChild(item.element);
                    }
                }
            });

            newItems.forEach(item => {
                if(!item.element) {
                    const div = document.createElement('div');
                    div.className = 'log-entry';
                    div.dataset.level = item.data.level || 'UNKNOWN';
                    div.dataset.sid = item.$index;
                    div.style.borderLeftColor = getLevelColor(item.data.level || 'UNKNOWN');
                    div.style.position = 'fixed';
                    div.style.left = '-9999px';
                    div.style.top = '-9999px';

                    div.innerHTML = `
                        ${(item.data.timestamp || item.data.tag) ? `
                        <span class="log-meta">
                            ${item.data.timestamp ? `<span class="log-timestamp">${item.data.timestamp}</span>` : ''}
                            ${item.data.tag ? `<span class="log-tag">${item.data.tag}</span>` : ''}
                        </span>
                        ` : ''}
                        <span class="log-level" style="color: ${getLevelColor(item.data.level || 'UNKNOWN')}">
                            [${item.data.level || 'UNKNOWN'}]
                        </span>
                        <span class="log-message">${item.data.message}</span>
                    `;

                    item.element = div;
                    logViewport.appendChild(div);
                }

                if(!item.invisible) {
                    item.element.style.position = '';
                    item.element.style.left = '';
                    item.element.style.top = '';
                }
            });

            oldItems = newItems;
        },
        consumer: {
            name: 'logthing',
            version: '1.0.0'
        }
    });

    window.updateVScroll = () => {
        workflow.call({
            process: 'adapter.reload',
            status: 'start',
            payload: {}
        });
    };
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

    filterButtons.forEach(button => {
        button.classList.toggle('active', activeFilters.includes(button.dataset.level));
    });

    if(window.updateVScroll) {
        window.updateVScroll();
    }
}
