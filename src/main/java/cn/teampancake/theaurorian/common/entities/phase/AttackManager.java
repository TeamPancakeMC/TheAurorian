package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.monster.MultiPhaseAttacker;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttackManager<T extends LivingEntity & MultiPhaseAttacker> {

    private final T entity;
    private final List<AttackPhase<T>> phaseList = new ArrayList<>();
    private final Int2ObjectArrayMap<List<AttackPhase<T>>> phases = new Int2ObjectArrayMap<>();
    private final IntArrayList priorities = new IntArrayList();
    private final Int2IntArrayMap coolDowns = new Int2IntArrayMap();

    public Int2IntArrayMap getCoolDowns() {
        return this.coolDowns;
    }

    public AttackManager(T entity, List<AttackPhase<T>> phaseList) {
        this.entity = entity;
        this.phaseList.addAll(phaseList);
        for (AttackPhase<T> phase : phaseList) {
            if (!this.phases.containsKey(phase.getPriority())) {
                this.phases.put(phase.getPriority(), new ArrayList<>());
            }
            if (!this.priorities.contains(phase.getPriority())) {
                this.priorities.add(phase.getPriority());
            }
            this.phases.get(phase.getPriority()).add(phase);
        }
        this.priorities.sort((i1, i2) -> i1 - i2);
    }
    
    public void tick() {
        if (this.entity.getAttackState() == 0) {
            selectPhase().ifPresent(p -> p.start(this.entity));
        } else {
            getActivePhase().ifPresent(p -> {
                if (!canContinue(p)) {
                    p.stop(this.entity);
                    this.coolDowns.put(p.getId(), p.getCoolDown());
                } else {
                    p.tick(this.entity);
                    this.entity.setAttackTicks(this.entity.getAttackTicks() + 1);
                }
            });
        }
        for (int id : this.coolDowns.keySet()) {
            this.coolDowns.put(id, Math.max(0, this.coolDowns.get(id) - 1));
        }
    }
    
    private Optional<AttackPhase<T>> selectPhase() {
        for (int priority : this.priorities) {
            List<AttackPhase<T>> phasesForPriority = this.phases.get(priority).stream().filter(p -> p.canStart(this.entity, this.coolDowns.getOrDefault(p.getId(), 0) <= 0)).toList();
            if (!phasesForPriority.isEmpty()) {
                return Optional.ofNullable(phasesForPriority.get(this.entity.getRandom().nextInt(phasesForPriority.size())));
            }
        }
        return Optional.empty();
    }
    
    private Optional<AttackPhase<T>> getActivePhase() {
        return this.phaseList.stream().filter(p -> entity.getAttackState() == p.getId()).findFirst();
    }

    private boolean canContinue(AttackPhase<T> phase) {
        return phase.canContinue(this.entity) && this.entity.getAttackTicks() <= phase.getDuration(this.entity);
    }

}