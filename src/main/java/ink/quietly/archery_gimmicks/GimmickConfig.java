package ink.quietly.archery_gimmicks;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class GimmickConfig extends WrappedConfig {
	@Comment("enables secret joke feature that makes typing \"*teleports behind you*\" do exactly that")
	public boolean sillyMode = true;
}
