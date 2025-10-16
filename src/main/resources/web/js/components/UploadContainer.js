// noinspection JSUnusedGlobalSymbols, CssUnusedSymbol

import { css, html, LitElement } from '/web/assets/lib/lit-core.min.js';

class UploadContainer extends LitElement {
    static styles = css`
        .upload-container {
            background-color: var(--button-background);
            border-radius: var(--border-radius);
            border: none;
            overflow: hidden;
            margin-bottom: 2.5rem;
        }

        .upload-form {
            display: flex;
            flex-direction: column;
            gap: 0;
        }

        .file-input-wrapper {
            border-radius: 8px 8px 0 0;
            border-left: 2px solid var(--border-color);
            border-top: 2px solid var(--border-color);
            border-right: 2px solid var(--border-color);
            padding: 3rem 2rem;
            transition: border-color var(--transition-speed) var(--transition-curve);
            background-image: radial-gradient(circle, var(--button-hover-background) 1px, transparent 1px);
            background-size: 15px 15px;
        }

        .file-input-wrapper:hover {
            border-color: var(--accent-color);
        }

        .file-label {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 1rem;
            cursor: pointer;
            color: var(--text-muted-color);
        }

        .upload-icon {
            width: 48px;
            height: 48px;
            color: var(--border-color);
            transition: color var(--transition-speed) var(--transition-curve);
        }

        .file-input-wrapper:hover .upload-icon {
            color: var(--accent-color);
        }

        .file-label span {
            font-weight: 500;
            color: var(--text-color);
        }

        .file-name {
            color: var(--text-muted-color);
            font-family: var(--font-mono);
        }

        .file-input {
            display: none;
        }

        .upload-button {
            background-color: var(--accent-color);
            color: var(--accent-color-text);
            border: none;
            outline: none;
            padding: 1rem 1.5rem;
            font-size: 0.9rem;
            font-weight: 500;
            cursor: pointer;
            transition: opacity var(--transition-speed) var(--transition-curve);
            border-radius: 0;
        }

        .upload-button:hover {
            opacity: 80%;
        }

        .upload-progress {
            padding: 1rem 1.5rem;
            border-top: 1px solid var(--border-color);
        }

        .progress-bar-container {
            background-color: var(--button-hover-background);
            border-radius: 4px;
            overflow: hidden;
            height: 8px;
            margin-bottom: 0.5rem;
        }

        .progress-bar {
            width: 0;
            height: 100%;
            background-color: var(--accent-color);
            transition: width 0.3s var(--transition-curve);
        }

        .progress-text {
            font-family: var(--font-mono);
            font-size: 0.875rem;
        }
    `;

    render() {
        return html`
            <div class="upload-container">
                <form class="upload-form" id="upload-form" action="/api/upload" method="POST"
                      enctype="multipart/form-data">
                    <div class="file-input-wrapper">
                        <label for="file" class="file-label">
                            <img src="/web/assets/upload.svg" class="upload-icon" width="64" height="64" alt="Upload">
                            <span class="file-name">Choose a file or drag it here</span>
                        </label>
                        <input type="file" name="file" id="file" class="file-input" multiple>
                    </div>
                    <button type="submit" class="upload-button">Upload and Process</button>
                </form>
                <div class="upload-progress" id="upload-progress" style="display: none;">
                    <div class="progress-bar-container">
                        <div class="progress-bar" id="progress-bar"></div>
                    </div>
                    <span class="progress-text" id="progress-text">0%</span>
                </div>
            </div>
        `;
    }

    firstUpdated() {
        const uploadForm = this.shadowRoot.getElementById('upload-form');
        const fileInput = this.shadowRoot.getElementById('file');
        const fileName = this.shadowRoot.querySelector('.file-name');
        const uploadProgress = this.shadowRoot.getElementById('upload-progress');
        const progressBar = this.shadowRoot.getElementById('progress-bar');
        const progressText = this.shadowRoot.querySelector('.progress-text');
        const fileInputWrapper = this.shadowRoot.querySelector('.file-input-wrapper');

        uploadForm.addEventListener('submit', (event) => {
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
                if(!event.lengthComputable)
                    return;

                const percentComplete = (event.loaded / event.total) * 100;

                progressBar.style.width = percentComplete + '%';
                progressText.textContent = Math.round(percentComplete) + '%';
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

        fileInputWrapper.addEventListener('dragover', (event) => {
            event.preventDefault();
            fileInputWrapper.style.borderColor = 'var(--accent-color)';
        });

        fileInputWrapper.addEventListener('dragleave', () => {
            fileInputWrapper.style.borderColor = 'var(--border-color)';
        });

        fileInputWrapper.addEventListener('drop', (event) => {
            event.preventDefault();
            fileInputWrapper.style.borderColor = 'var(--border-color)';
            fileInput.files = event.dataTransfer.files;
            fileInput.dispatchEvent(new Event('change'));
        });
    }
}

customElements.define('upload-container-component', UploadContainer);