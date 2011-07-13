package com.elmakers.mine.bukkit.plugins.magic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerSpells 
{
    protected Player player;
	protected HashMap<String, Spell> spells = new HashMap<String, Spell>();
    private final List<Spell>                   movementListeners              = new ArrayList<Spell>();
    private final List<Spell>                   quitListeners                  = new ArrayList<Spell>();
    private final List<Spell>                   deathListeners                 = new ArrayList<Spell>();
    private final List<Spell>                   damageListeners                = new ArrayList<Spell>();

    public void registerEvent(SpellEventType type, Spell spell)
    {
        switch (type)
        {
            case PLAYER_MOVE:
                if (!movementListeners.contains(spell))
                    movementListeners.add(spell);
                break;
            case PLAYER_QUIT:
                if (!quitListeners.contains(spell))
                    quitListeners.add(spell);
                break;
            case PLAYER_DAMAGE:
                if (!damageListeners.contains(spell))
                    damageListeners.add(spell);
                break;
            case PLAYER_DEATH:
                if (!deathListeners.contains(spell))
                    deathListeners.add(spell);
                break;
        }
    }

    public void unregisterEvent(SpellEventType type, Spell spell)
    {
        switch (type)
        {
            case PLAYER_MOVE:
                movementListeners.remove(spell);
                break;
            case PLAYER_DAMAGE:
                damageListeners.remove(spell);
                break;
            case PLAYER_QUIT:
                quitListeners.remove(spell);
                break;
            case PLAYER_DEATH:
                deathListeners.remove(spell);
                break;
        }
    }
    
	public PlayerSpells(Player player)
	{
	    this.player = player;
	}
	
	public Spell getSpell(String name)
	{
	    return spells.get(name);
	}
	
	protected void addSpell(Spell spell)
	{
	    spells.put(spell.getName(), spell);
	}
	
	public void cancel()
	{
	    for (Spell spell : spells.values())
        {
            spell.cancel();
        }
	}

    public void onPlayerQuit(PlayerQuitEvent event)
    {
        // Must allow listeners to remove themselves during the event!
        List<Spell> active = new ArrayList<Spell>();
        active.addAll(quitListeners);
        for (Spell listener : active)
        {
            listener.onPlayerQuit(event);
        }
    }

    public void onPlayerMove(PlayerMoveEvent event)
    {
        // Must allow listeners to remove themselves during the event!
        List<Spell> active = new ArrayList<Spell>();
        active.addAll(movementListeners);
        for (Spell listener : active)
        {
            listener.onPlayerMove(event);
        }
    }

    public void onPlayerDeath(EntityDeathEvent event)
    {
        List<Spell> active = new ArrayList<Spell>();
        active.addAll(deathListeners);
        for (Spell listener : active)
        {
            if (player == listener.getPlayer())
            {
                listener.onPlayerDeath(event);
            }
        }
    }

    public void onPlayerDamage(EntityDamageEvent event)
    {
        List<Spell> active = new ArrayList<Spell>();
        active.addAll(damageListeners);
        for (Spell listener : active)
        {
            if (player == listener.getPlayer())
            {
                listener.onPlayerDamage(event);
            }
        }
    }
}
