import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'

import './global.css';

import './animation.css';
import App from './App.tsx'
import {AuthProvider} from "./context/AuthContext.tsx";
import {BrowserRouter} from "react-router-dom";
import "./i18n.ts";
import {UserProvider} from "./context/UserContext.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter >
          <AuthProvider>
              <UserProvider >
                  <App />
              </UserProvider>
          </AuthProvider>
      </BrowserRouter>
  </StrictMode>,
)