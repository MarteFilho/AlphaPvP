package Domain.KitsDefault;

import Domain.Kit;
import org.bukkit.Material;

public class None extends Kit {
    public None() {
        super("Nenhum", "Espada com sharpness!", Material.BARRIER, null, 0, 0);
    }

    @Override
    public void RemoveAbility() {

    }

    @Override
    public boolean HasItem() {
        return false;
    }
}
