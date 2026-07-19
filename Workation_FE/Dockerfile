# syntax=docker/dockerfile:1

# --- Build stage: produce the production Angular bundle ---
FROM node:24-alpine AS build
WORKDIR /app

# Cache deps: copy manifests first, install, then copy sources.
COPY package.json package-lock.json ./
RUN npm ci

COPY . .
RUN npm run build

# --- Runtime stage: serve the static bundle with nginx ---
FROM nginx:alpine AS runtime

# SPA routing + /workflex API proxy to the backend container.
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/Workation_FE/browser /usr/share/nginx/html

EXPOSE 80
