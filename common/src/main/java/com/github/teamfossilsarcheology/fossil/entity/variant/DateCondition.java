package com.github.teamfossilsarcheology.fossil.entity.variant;

import com.google.gson.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * With this condition a variant may be applied on a certain date.
 * Required json entries: <b>type: "date"</b>, <b>month and/or day</b>
 * Optional json entries: <b>chance</b>(Default: 1)
 */
public class DateCondition extends VariantCondition {
    private static final int MONTH_ONLY = 0;
    private static final int DAY_ONLY = 1;
    private static final int MONTH_AND_DAY = 2;
    private static final String MONTH_KEY = "month";
    private static final String DAY_KEY = "day";
    private final int mode;
    private final LocalDate date;

    private DateCondition(double chance, int mode, LocalDate date) {
        super(chance);
        this.mode = mode;
        this.date = date;
    }

    /**
     * Depending on the mode this method may only compare the day or month
     *
     * @param now the date that will be compared to. Only month and day will be checked
     * @return {@code true} if the date matches and the random check succeeds
     */
    public boolean test(RandomSource random, ZonedDateTime now) {
        if (random.nextDouble() > chance) {
            return false;
        }
        return switch (mode) {
            case MONTH_ONLY -> now.getMonth() == date.getMonth();
            case DAY_ONLY -> now.getDayOfMonth() == date.getDayOfMonth();
            case MONTH_AND_DAY -> now.getMonth() == date.getMonth() && now.getDayOfMonth() == date.getDayOfMonth();
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DateCondition that = (DateCondition) object;
        return chance == that.chance && mode == that.mode && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chance, mode, date);
    }

    public static class Serializer implements VariantCondition.Serializer<DateCondition> {
        @Override
        public DateCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject root = json.getAsJsonObject();
            double chance = GsonHelper.getAsDouble(root, "chance", 1);
            if (root.has(DAY_KEY)) {
                int day = root.get(DAY_KEY).getAsInt();
                if (root.has(MONTH_KEY)) {
                    LocalDate date = LocalDate.of(2025, root.get(MONTH_KEY).getAsInt(), day);
                    return new DateCondition(chance, MONTH_AND_DAY, date);
                } else {
                    LocalDate date = LocalDate.of(2025, Month.JANUARY, day);
                    return new DateCondition(chance, DAY_ONLY, date);
                }
            } else if (root.has(MONTH_KEY)) {
                LocalDate date = LocalDate.of(2025, root.get(MONTH_KEY).getAsInt(), 1);
                return new DateCondition(chance, MONTH_ONLY, date);
            } else {
                throw new JsonSyntaxException("Missing " + MONTH_KEY + " or " + DAY_KEY + " entry");
            }
        }

        @Override
        public void save(CompoundTag tag, DateCondition condition) {
            tag.putDouble("Chance", condition.chance);
            tag.putInt("Mode", condition.mode);
            tag.putInt("Month", condition.date.getMonthValue());
            tag.putInt("Day", condition.date.getDayOfMonth());
        }

        @Override
        public DateCondition load(CompoundTag tag) {
            LocalDate date = LocalDate.of(LocalDate.now().getYear(), tag.getInt("Month"), tag.getInt("Day"));
            return new DateCondition(tag.getDouble("Chance"), tag.getInt("Mode"), date);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, DateCondition condition) {
            buf.writeDouble(condition.chance);
            buf.writeInt(condition.mode);
            buf.writeInt(condition.date.getMonthValue());
            buf.writeInt(condition.date.getDayOfMonth());
        }

        @Override
        public DateCondition fromNetwork(FriendlyByteBuf buf) {
            return new DateCondition(buf.readDouble(), buf.readInt(), LocalDate.of(LocalDate.now().getYear(), buf.readInt(), buf.readInt()));
        }
    }
}
