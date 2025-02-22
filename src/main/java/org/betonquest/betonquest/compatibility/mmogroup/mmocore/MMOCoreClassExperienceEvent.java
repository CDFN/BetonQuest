package org.betonquest.betonquest.compatibility.mmogroup.mmocore;

import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.VariableNumber;
import org.betonquest.betonquest.api.QuestEvent;
import org.betonquest.betonquest.api.profiles.Profile;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.QuestRuntimeException;

@SuppressWarnings("PMD.CommentRequired")
public class MMOCoreClassExperienceEvent extends QuestEvent {

    private final VariableNumber amountVar;
    private final boolean isLevel;

    public MMOCoreClassExperienceEvent(final Instruction instruction) throws InstructionParseException {
        super(instruction, true);

        amountVar = instruction.getVarNum();
        isLevel = instruction.hasArgument("level");
    }

    @Override
    protected Void execute(final Profile profile) throws QuestRuntimeException {
        final int amount = amountVar.getInt(profile);
        final PlayerData mmoData = PlayerData.get(profile.getPlayerUUID());

        if (isLevel) {
            mmoData.giveLevels(amount, EXPSource.QUEST);
        } else {
            mmoData.giveExperience(amount, EXPSource.QUEST);
        }
        return null;
    }
}
