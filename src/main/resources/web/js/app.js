document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("form")

    if(!form)
        return

    form.addEventListener("submit", async event => {
        event.preventDefault()

        await submit()
    })

    async function submit() {
        const fileInput = document.getElementById('file-input');
        const selectedFile = fileInput.files[0];
        const reader = new FileReader();

        if(!selectedFile.name.includes(".log")) {
            alert("file must be of type log")

            return;
        }

        reader.onload = async event => {
            const fileContent = event.target.result;

            const post = await fetch('/api/process', {
                method: "POST",
                body: fileContent,
            });

            alert(
                await post.text()
            );
        };

        reader.readAsText(selectedFile);
    }
})
