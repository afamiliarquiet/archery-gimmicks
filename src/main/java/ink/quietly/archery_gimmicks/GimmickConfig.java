package ink.quietly.archery_gimmicks;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class GimmickConfig extends WrappedConfig {
	@Comment("enables secret joke feature that makes typing \"*teleports behind you*\" do exactly that")
	public boolean sillyMode = true;

	@Comment("Ancient arrows are called in a disk in the sky around an enchanted arrow's landing.")
	@Comment("The range controls the size of the disk it can be spawned in.")
	public int ancientArrowCallingRadius = 512;
	@Comment("The height controls the distance above the enchanted arrow the spawning disk is placed.")
	public float ancientArrowCallingHeight = 1000;
	@Comment("Finally, this controls the speed the ancient arrow is spawned with.")
	public float ancientArrowCallingPower = 10;
}
