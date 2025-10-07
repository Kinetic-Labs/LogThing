document.addEventListener('DOMContentLoaded', async () => {
    const content = document.getElementById('content');

    const get = await fetch('/api/load', {
        method: 'GET',
    }).then(response => response.text());

    console.log("get: ", get);
    content.innerText = get;
});
