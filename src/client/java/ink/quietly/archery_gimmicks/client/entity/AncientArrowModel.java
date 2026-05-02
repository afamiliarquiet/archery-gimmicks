package ink.quietly.archery_gimmicks.client.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.object.projectile.ArrowModel;

public class AncientArrowModel extends ArrowModel {
	public AncientArrowModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		// basically the same as ArrowModel, except nudged -2 on the x axis.
		// makes the arrow stick into terrain a bit less
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild(
			"back",
			CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F),
			PartPose.offsetAndRotation(-13.0F, 0.0F, 0.0F, (float) (Math.PI / 4), 0.0F, 0.0F).withScale(0.8F)
		);
		CubeListBuilder cross = CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -2.0F, 0.0F, 16.0F, 4.0F, 0.0F, CubeDeformation.NONE, 1.0F, 0.8F);
		root.addOrReplaceChild("cross_1", cross, PartPose.rotation((float) (Math.PI / 4), 0.0F, 0.0F));
		root.addOrReplaceChild("cross_2", cross, PartPose.rotation((float) (Math.PI * 3.0 / 4.0), 0.0F, 0.0F));
//		CubeListBuilder shaft = CubeListBuilder.create().texOffs(0, 0).addBox(-13f, -0.25f, -0.25f, 15, 0.5f, 0.5f);
//		root.addOrReplaceChild("shaft", shaft, PartPose.rotation((float) (Math.PI / 4), 0, 0));
//
//		CubeListBuilder tip = CubeListBuilder.create().texOffs(0, 0).addBox(-1, -0.25f, -1.25f, 2, 0.5f, 1);
//		root.addOrReplaceChild("tip_1", tip, PartPose.rotation((float) (Math.PI / 4), 0, 0));
//		root.addOrReplaceChild("tip_2", tip, PartPose.rotation((float) (3 * Math.PI / 4), 0, 0));
//		root.addOrReplaceChild("tip_3", tip, PartPose.rotation((float) (5 * Math.PI / 4), 0, 0));
//		root.addOrReplaceChild("tip_4", tip, PartPose.rotation((float) (7 * Math.PI / 4), 0, 0));

//		CubeListBuilder fletchingSlice = CubeListBuilder.create()

		return LayerDefinition.create(mesh.transformed(pose -> pose.scaled(0.9F)), 32, 32);
	}
}
