import { LitElement, html, css } from 'https://cdn.jsdelivr.net/gh/lit/dist@3/core/lit-core.min.js';

class MainHeader extends LitElement {
  static properties = {
    headerTitle: { type: String, attribute: 'header-title' },
  };

  static styles = css`
    .main-header h2 {
      font-size: 1.75rem;
      font-weight: 600;
      margin-bottom: 2.5rem;
    }
  `;

  render() {
    return html`
      <header class="main-header">
        <h1>${this.headerTitle}</h1>
      </header>
    `;
  }
}

customElements.define('main-header-component', MainHeader);
