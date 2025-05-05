FROM caddy:alpine

# Create the caddy user and group explicitly
RUN addgroup -S caddy && adduser -S -G caddy caddy

# Create necessary directories for Caddy (config and data)
RUN mkdir -p /config/caddy /data/caddy/locks && \
    chown -R caddy:caddy /config /data

# Copy static site content and config with proper ownership
COPY --chown=caddy:caddy html/ /usr/share/caddy/
COPY --chown=caddy:caddy Caddyfile /etc/caddy/Caddyfile

# Set stricter file permissions
RUN chmod 644 /etc/caddy/Caddyfile && \
    find /usr/share/caddy -type f -exec chmod 644 {} \; && \
    find /usr/share/caddy -type d -exec chmod 755 {} \;

# Switch to non-root user for security
USER caddy

# Start Caddy
CMD ["caddy", "run", "--config", "/etc/caddy/Caddyfile", "--adapter", "caddyfile"]
