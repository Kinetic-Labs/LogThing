document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("form")

    if(!form) {
        return
    }

    form.addEventListener("submit", async event => {
        event.preventDefault()

        await submit()
    })

    async function submit() {
        const formData = new FormData(form);
        const file = formData.get('file');

        if(!file)
            return;

        if(!file.name.includes(".log")) {
            alert("file must be of type log")

            return;
        }

        const post = await fetch('/api/process', {
            method: "POST",
            body: formData,
        });

        alert(
            await post.text()
        );
    }
})
