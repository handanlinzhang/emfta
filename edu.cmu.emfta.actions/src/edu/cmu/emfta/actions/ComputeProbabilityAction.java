package edu.cmu.emfta.actions;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.action.AbstractExternalJavaAction;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DNodeSpec;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DSemanticDiagramSpec;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import edu.cmu.emfta.Event;

public class ComputeProbabilityAction extends AbstractExternalJavaAction {

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {

//		System.out.println("[CutSetAction] calling execute");
		for (EObject eo : selections) {
			EObject target = null;

			System.out.println("[ProbabilityConsistencyAction] eobject = " + eo);

			if (eo instanceof DSemanticDiagramSpec) {
				DSemanticDiagramSpec ds = (DSemanticDiagramSpec) eo;
				target = ds.getTarget();

				System.out.println("[ProbabilityConsistencyAction] target = " + target);
			}

			if (eo instanceof DNodeSpec) {
				DNodeSpec ds = (DNodeSpec) eo;
				target = ds.getTarget();

				System.out.println("[ProbabilityConsistencyAction] target = " + target);
			}

			if (target != null) {
				System.out.println("[ProbabilityConsistencyAction] Check Probability for event = " + target);
				Utils.getProbability((Event) target);
				return;
			}

			MessageBox dialog = new MessageBox(Display.getDefault().getActiveShell(), SWT.ERROR | SWT.ICON_ERROR);
			dialog.setText("Error");
			dialog.setMessage("Please select an event in the FTA tree");

			dialog.open();

		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {

		/**
		 * For now, we return true all the time. Might need to optimize
		 * it to make it more user-friendly.
		 */
//		System.out.println("[CutSetAction] calling canExecute");
		for (EObject eo : selections) {
//			System.out.println("[CutSetAction] eobject class= " + eo.getClass());

			if (eo instanceof DSemanticDiagramSpec) {
				DSemanticDiagramSpec ds = (DSemanticDiagramSpec) eo;
				EObject target = ds.getTarget();

//				System.out.println("[CutSetAction] eobject class= " + eo.getClass());
//
//				System.out.println("[CutSetAction] target = " + target);
			}

			if (eo instanceof DNodeSpec) {
				DNodeSpec ds = (DNodeSpec) eo;
				EObject target = ds.getTarget();

//				System.out.println("[CutSetAction] eobject class= " + eo.getClass());
//
//				System.out.println("[CutSetAction] target = " + target);

				if (target instanceof edu.cmu.emfta.Event) {
					return true;
				}

				if (target instanceof edu.cmu.emfta.Tree) {
					return true;
				}

				if (target instanceof edu.cmu.emfta.FTAModel) {
					return true;
				}
			}

		}
		return false;
	}

}