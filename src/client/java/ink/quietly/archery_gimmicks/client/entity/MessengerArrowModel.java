package ink.quietly.archery_gimmicks.client.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.object.projectile.ArrowModel;

public class MessengerArrowModel extends ArrowModel {
	public MessengerArrowModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		// was considering having a dangling bit for the end of the paper wrapped around the arrow..
		// but it looks weird. maybe if someone else joins for texture/modelstuff.

		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild(
			"back",
			CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F),
			PartPose.offsetAndRotation(-11.0F, 0.0F, 0.0F, (float) (Math.PI / 4), 0.0F, 0.0F).withScale(0.8F)
		);
		CubeListBuilder cross = CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -2.0F, 0.0F, 16.0F, 4.0F, 0.0F, CubeDeformation.NONE, 1.0F, 0.8F);
		root.addOrReplaceChild("cross_1", cross, PartPose.rotation((float) (Math.PI / 4), 0.0F, 0.0F));
		root.addOrReplaceChild("cross_2", cross, PartPose.rotation((float) (Math.PI * 3.0 / 4.0), 0.0F, 0.0F));
		CubeListBuilder wrap = CubeListBuilder.create().texOffs(0, 10).addBox(-7.0F, -1F, 0.0F, 7.0F, 2F, 0.0F, CubeDeformation.NONE, 1.0F, 1.0F);
		root.addOrReplaceChild("wrap_1", wrap, PartPose.offsetAndRotation(0, 0, 1f, (float) Math.PI, 0, 0));
		root.addOrReplaceChild("wrap_2", wrap, PartPose.offsetAndRotation(0, 0, -1f, 0, 0, 0));
		root.addOrReplaceChild("wrap_3", wrap, PartPose.offsetAndRotation(0, 1f, 0, (float) Math.PI / 2, 0, 0));
		root.addOrReplaceChild("wrap_4", wrap, PartPose.offsetAndRotation(0, -1f, 0, (float) -Math.PI / 2, 0, 0));
//		CubeListBuilder dangler = CubeListBuilder.create().texOffs(0, 12).addBox(-7.0F, -1F, 0.0F, 7.0F, 1F, 0.0F, CubeDeformation.NONE, 1.0F, 1.0F);
//		root.addOrReplaceChild("dangler", dangler, PartPose.offset(0, -1f, 1f));

		return LayerDefinition.create(mesh.transformed(pose -> pose.scaled(0.9F)), 32, 32);
	}

//	@Override
//	public void setupAnim(@NonNull ArrowRenderState state) {
//		super.setupAnim(state);
//		ModelPart dangler = this.getChildPart("dangler");
//		if (dangler != null) {
//			if (state.xRot > 45) {
//				dangler.x--;
//			} else if (state.xRot < -45) {
//				dangler.x++;
//			}
//		}
//	}
}
