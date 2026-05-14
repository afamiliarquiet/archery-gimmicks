package ink.quietly.archery_gimmicks;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class GimmickConfig extends WrappedConfig {
	@Comment("enables secret joke feature that makes typing \"*teleports behind you*\" do exactly that")
	public boolean sillyMode = true;

	@Comment("Ancient arrows are called in a dome in the sky around an enchanted arrow's landing.")
	@Comment("The range controls the size of the dome it can be spawned in.")
	public int ancientArrowCallingRadius = 512;
	@Comment("The height controls the distance above the enchanted arrow the spawning dome is placed.")
	public float ancientArrowCallingHeight = 1000;
	@Comment("Finally, this controls the speed the ancient arrow is spawned with.")
	public float ancientArrowCallingPower = 10;

	@Comment("Scales the default arrow render distance. 1 matches vanilla's arrow render distance")
	@Comment("When using the Heavy Bow, it's very easy to lose track of your arrows, so this is raised by default.")
	public float arrowRenderDistanceScale = 3;
}
