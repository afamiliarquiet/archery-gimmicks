package ink.quietly.archery_gimmicks.client.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.object.projectile.ArrowModel;

public class BugArrowModel extends ArrowModel {
	public BugArrowModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
//		root.addOrReplaceChild(
//			"back",
//			CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F),
//			PartPose.offsetAndRotation(-11.0F, 0.0F, 0.0F, (float) (Math.PI / 4), 0.0F, 0.0F).withScale(0.8F)
//		);
//		CubeListBuilder cross = CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -2.0F, 0.0F, 16.0F, 4.0F, 0.0F, CubeDeformation.NONE, 1.0F, 0.8F);
//		root.addOrReplaceChild("cross_1", cross, PartPose.rotation((float) (Math.PI / 4), 0.0F, 0.0F));
//		root.addOrReplaceChild("cross_2", cross, PartPose.rotation((float) (Math.PI * 3.0 / 4.0), 0.0F, 0.0F));
		var head = root.addOrReplaceChild(
			"bug",
			CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F),
			PartPose.offsetAndRotation(-4f, 0f, 0f, (float) (1 * Math.PI / 2), (float) (1 * Math.PI / 2), (float) (3 * Math.PI / 2))
//			PartPose.ZERO
		);

		head.addOrReplaceChild("bug_hat", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

		return LayerDefinition.create(mesh.transformed(pose -> pose.scaled(0.9F)), 32, 32);
	}
}
