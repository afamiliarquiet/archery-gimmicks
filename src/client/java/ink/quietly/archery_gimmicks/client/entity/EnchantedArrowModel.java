package ink.quietly.archery_gimmicks.client.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.object.projectile.ArrowModel;

public class EnchantedArrowModel extends ArrowModel {
	public EnchantedArrowModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		// basically the same as ArrowModel, except inflated by 0.0005f to mitigate a bit of flickering.
		// comes at the cost of strands of floating pixels that are barely visible when very close,
		// and the flickering still comes back when you're more than a few blocks away.
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild(
			"back",
			CubeListBuilder.create().texOffs(0, 0).addBox(-0.00025F, -2.5F, -2.5F, 0.0005F, 5.0F, 5.0F),
			PartPose.offsetAndRotation(-11.0F, 0.0F, 0.0F, (float) (Math.PI / 4), 0.0F, 0.0F).withScale(0.8F)
		);
		CubeListBuilder cross = CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -2.0F, -0.00025F, 16.0F, 4.0F, 0.0005F, CubeDeformation.NONE, 1.0F, 0.8F);
		root.addOrReplaceChild("cross_1", cross, PartPose.rotation((float) (Math.PI / 4), 0.0F, 0.0F));
		root.addOrReplaceChild("cross_2", cross, PartPose.rotation((float) (Math.PI * 3.0 / 4.0), 0.0F, 0.0F));

		return LayerDefinition.create(mesh.transformed(pose -> pose.scaled(0.9F)), 32, 32);
	}
}
