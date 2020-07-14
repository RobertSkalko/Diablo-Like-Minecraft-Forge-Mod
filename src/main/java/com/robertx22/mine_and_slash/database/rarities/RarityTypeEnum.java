package com.robertx22.mine_and_slash.database.rarities;

import com.robertx22.mine_and_slash.database.rarities.gears.CommonGear;
import com.robertx22.mine_and_slash.database.rarities.mobs.CommonMob;
import com.robertx22.mine_and_slash.database.rarities.skill_gems.CommonGem;
import com.robertx22.mine_and_slash.db_lists.Rarities;
import com.robertx22.mine_and_slash.onevent.data_gen.ISerializable;

public enum RarityTypeEnum {

    GEAR("gear", CommonGear.getInstance(), Rarities.Gears),
    MOB("mob", CommonMob.getInstance(), Rarities.Mobs),
    SKILL_GEM("skill_gem", new CommonGem(), Rarities.SkillGems);;

    public String id;
    public ISerializable serializer;
    public BaseRaritiesContainer container;

    RarityTypeEnum(String id, ISerializable serializer, BaseRaritiesContainer container) {
        this.id = id;
        this.serializer = serializer;
        this.container = container;
    }

}
