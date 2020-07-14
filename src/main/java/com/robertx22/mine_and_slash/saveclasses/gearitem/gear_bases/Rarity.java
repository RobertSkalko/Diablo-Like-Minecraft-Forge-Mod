package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.rarities.serialization.SerializedBaseRarity;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.onevent.data_gen.ISerializable;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;
import net.minecraft.util.text.TextFormatting;

public interface Rarity extends IWeighted, IAutoLocName, ISerializable<Rarity> {

    @Override
    default JsonObject toJson() {
        return null;
    }

    @Override
    default Rarity fromJson(JsonObject json) {
        return null;
    }

    default JsonObject getRarityJsonObject() {
        JsonObject json = getDefaultJson();

        json.addProperty("rank", Rank());
        json.addProperty("loc_name_internal", locNameForLangFile());

        json.addProperty("text_formatting", textFormatting().name());

        return json;
    }

    default SerializedBaseRarity baseSerializedRarityFromJson(JsonObject json) {

        SerializedBaseRarity obj = new SerializedBaseRarity();

        obj.locNameID = getLangNameStringFromJson(json);
        obj.weight = getWeightFromJson(json);

        obj.locName = json.get("loc_name_internal")
            .getAsString();
        obj.rank = json.get("rank")
            .getAsInt();
        obj.textFormatting = TextFormatting.valueOf(json.get("text_formatting")
            .getAsString());

        return obj;
    }

    String GUID();

    int Rank();

    default String Color() {
        return textFormatting().toString();
    }

    int Weight();

    TextFormatting textFormatting();

    @Override
    public default String locNameLangFileGUID() {
        return Ref.MODID + ".rarity." + formattedGUID();
    }

    @Override
    public default AutoLocGroup locNameGroup() {
        return AutoLocGroup.Rarities;
    }

}
