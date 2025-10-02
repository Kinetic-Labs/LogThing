document.addEventListener("DOMContentLoaded", () => {
    console.log("DOMContentLoaded")

    const form = document.getElementById("form")

    if(!form) {
        console.error("unable to find things")
        return
    }

    form.addEventListener("submit", async event => {
        event.preventDefault()
        await submit()
    })

    async function submit() {
        const formData = new FormData(form);
        const file = formData.get('file');

        if(!file || !file.name.includes(".log")) {
            alert("file must be of type log")
            return;
        }

        const post = await fetch('/api/process', {
            method: "POST",
            body: formData,
        });

        alert(await post.text());
    }
})
