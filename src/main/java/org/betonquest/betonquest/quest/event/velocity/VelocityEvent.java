package org.betonquest.betonquest.quest.event.velocity;

import org.betonquest.betonquest.api.profiles.OnlineProfile;
import org.betonquest.betonquest.api.profiles.Profile;
import org.betonquest.betonquest.api.quest.event.Event;
import org.betonquest.betonquest.exceptions.QuestRuntimeException;
import org.betonquest.betonquest.utils.location.VectorData;
import org.bukkit.util.Vector;

import java.util.Optional;

/**
 * The velocity event. Throws the player around.
 */
public class VelocityEvent implements Event {
    /**
     * The vector of the direction and speed
     */
    private final VectorData vector;
    /**
     * Sets in which direction the vector is directed
     */
    private final VectorDirection direction;
    /**
     * Sets how the vector should get merged with the player-velocity
     */
    private final VectorModification modification;

    /**
     * @param vector       vector of the direction and speed
     * @param direction    direction in which the vector is directed
     * @param modification modification how the vector should get merged with the player-velocity
     */
    public VelocityEvent(final VectorData vector, final VectorDirection direction, final VectorModification modification) {
        this.vector = vector;
        this.direction = direction;
        this.modification = modification;
    }


    @Override
    public void execute(final Profile profile) throws QuestRuntimeException {
        final Optional<OnlineProfile> optionalOnlineProfile = profile.getOnlineProfile();
        if (optionalOnlineProfile.isPresent()) {
            final OnlineProfile onlineProfile = optionalOnlineProfile.get();
            final Vector pVector = vector.get(profile);
            final Vector directionVector = direction.calculate(onlineProfile.getPlayer(), pVector);
            final Vector modificationVector = modification.calculate(onlineProfile.getPlayer(), directionVector);
            onlineProfile.getPlayer().setVelocity(modificationVector);
        }
    }
}
