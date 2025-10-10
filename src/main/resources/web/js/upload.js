document.addEventListener('DOMContentLoaded', () => {
    const uploadForm = document.getElementById('upload-form');
    const fileInput = document.getElementById('file');
    const fileName = document.querySelector('.file-name');
    const uploadProgress = document.getElementById('upload-progress');
    const progressBar = document.getElementById('progress-bar');
    const progressText = document.getElementById('progress-text');
    const fileInputWrapper = document.querySelector('.file-input-wrapper');

    uploadForm.addEventListener('submit', async event => {
        event.preventDefault();

        const formData = new FormData();
        const xhr = new XMLHttpRequest();

        if(!fileInput.files.length) {
            alert('Please select a file to upload.');
            return;
        }

        for(const file of fileInput.files)
            formData.append('file', file);

        uploadProgress.style.display = 'block';

        xhr.open('POST', '/api/upload', true);

        xhr.upload.onprogress = (event) => {
            if(event.lengthComputable) {
                const percentComplete = (event.loaded / event.total) * 100;

                progressBar.style.width = percentComplete + '%';
                progressText.textContent = Math.round(percentComplete) + '%';
            }
        };

        xhr.onload = () => {
            if(xhr.status === 200) {
                progressText.textContent = 'Upload complete!';
                setTimeout(() => window.location.href = '/dashboard', 1000);
            } else {
                progressText.textContent = 'Upload failed.';
                console.error('Upload failed:', xhr.responseText);
            }
        };

        xhr.onerror = () => {
            progressText.textContent = 'An error occurred during the upload.';
            console.error('Upload error:', xhr.statusText);
        };

        xhr.send(formData);
    });

    fileInput.addEventListener('change', () => {
        if(fileInput.files.length > 0) {
            fileName.textContent = fileInput.files.length > 1
                ? `${fileInput.files.length} files selected`
                : fileInput.files[0].name;
        } else {
            fileName.textContent = 'Choose a file or drag it here';
        }
    });


    fileInputWrapper.addEventListener('dragover', event => {
        event.preventDefault();

        fileInputWrapper.style.borderColor = 'var(--accent-color)';
    });

    fileInputWrapper.addEventListener('dragleave', () => {
        fileInputWrapper.style.borderColor = 'var(--border-color)';
    });

    fileInputWrapper.addEventListener('drop', event => {
        event.preventDefault();

        fileInputWrapper.style.borderColor = 'var(--border-color)';
        fileInput.files = e.dataTransfer.files;
        fileInput.dispatchEvent(new Event('change'));
    });
});