import { LitElement, html, css } from 'https://cdn.jsdelivr.net/gh/lit/dist@3/core/lit-core.min.js';

class Sidebar extends LitElement {
  static properties = {
    activePage: { type: String, attribute: 'active-page' },
  };

  static styles = css`
    :host {
      display: flex;
      flex-direction: column;
      width: 300px;
      background-color: var(--sidebar-background);
      backdrop-filter: blur(16px);
      -webkit-backdrop-filter: blur(16px);
      border-right: 1px solid var(--border-color);
      padding: 1.5rem;
      flex-shrink: 0;
    }

    .sidebar-header {
      margin-bottom: 2.5rem;
    }

    .sidebar-title {
      font-size: 1.25rem;
      font-weight: 600;
      color: var(--text-color);
    }

    .sidebar-nav {
      display: flex;
      flex-direction: column;
      gap: 0.25rem;
      margin-bottom: 2.5rem;
    }

    a {
      color: var(--text-muted-color);
      text-decoration: none;
      padding: 0.6rem 0.8rem;
      border-radius: var(--border-radius);
      transition: background-color var(--transition-speed) var(--transition-curve), color var(--transition-speed) var(--transition-curve);
      font-weight: 500;
    }

    a:hover {
      background-color: var(--hover-color);
      color: var(--text-color);
    }

    a.active {
      background-color: var(--hover-color);
      color: var(--text-color);
    }
  `;

  render() {
    return html`
      <header class="sidebar-header">
        <h1 class="sidebar-title">LogThing</h1>
      </header>
      <nav class="sidebar-nav">
        <a href="/" class="${this.activePage === 'dashboard' ? 'active' : ''}">Dashboard</a>
        <!--<a href="/settings" class="${this.activePage === 'settings' ? 'active' : ''}">Settings</a>-->
      </nav>
      <slot></slot>
    `;
  }
}

customElements.define('sidebar-component', Sidebar);
