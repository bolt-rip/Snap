package de.themoep.snap.forwarding;

/*
 * Snap
 * Copyright (c) 2020 Max Lee aka Phoenix616 (max@themoep.de)
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import net.md_5.bungee.api.config.ServerInfo;

public class SnapServerList implements Map<String, ServerInfo> {

    private ProxyServer server;
    private Map<String, ServerInfo> delegate;

    public SnapServerList(ProxyServer server, Map<String, ServerInfo> delegate) {
        this.server = server;
        this.delegate = delegate;
    }

    @Override
    public void clear() {
        delegate.clear();
        for (RegisteredServer registered : server.getAllServers())
            server.unregisterServer(registered.getServerInfo());
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public Set<Entry<String, ServerInfo>> entrySet() {
        return delegate.entrySet();
    }

    @Override
    public ServerInfo get(Object key) {
        return delegate.get(key);
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return delegate.keySet();
    }

    @Override
    public ServerInfo put(String key, ServerInfo value) {
        server.registerServer(((SnapServerInfo) value).getServer().getServerInfo());

        return delegate.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends ServerInfo> m) {
        for (Entry<? extends String, ? extends ServerInfo> entry : m.entrySet())
            this.put(entry.getKey(), entry.getValue());
    }

    @Override
    public ServerInfo remove(Object key) {
        SnapServerInfo snap = (SnapServerInfo) delegate.remove(key);
        server.unregisterServer(snap.getServer().getServerInfo());

        return snap;
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public Collection<ServerInfo> values() {
        return delegate.values();
    }

}
