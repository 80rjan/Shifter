import {StepConnector, stepConnectorClasses, type StepIconProps, styled} from "@mui/material";
import Check from "@mui/icons-material/Check";
import React from "react";

const CustomStepperConnector = styled(StepConnector)(({ theme }) => ({
    [`&.${stepConnectorClasses.alternativeLabel}`]: {
        top: 10,
        left: 'calc(-50% + 16px)',
        right: 'calc(50% + 16px)',
    },
    [`&.${stepConnectorClasses.completed}`]: {
        [`& .${stepConnectorClasses.line}`]: {
            borderColor: 'var(--color-shifter)',
            opacity: 0.2
        },
    },
    [`&.${stepConnectorClasses.active}`]: {
        [`& .${stepConnectorClasses.line}`]: {
            borderColor: 'var(--color-shifter)',
            opacity: 0.2
        },
    },
    [`& .${stepConnectorClasses.line}`]: {
        borderColor: '#BBB',
        borderWidth: 2,
        borderRadius: 2,
        transition: 'border-color 0.4s ease-in-out, opacity 0.4s ease-in-out',
    },
}));
const CustomStepperStepIconRoot = styled('div')<{ ownerState: { active?: boolean; completed?: boolean } }>(
    ({ ownerState }) => ({
        color: '#eaeaf0',
        display: 'flex',
        position: 'relative',       // <-- needed for absolute positioning inside
        height: 22,
        alignItems: 'center',
        transition: 'color 0.3s ease-in-out',

        // CHECKMARK
        '& .CustomStepperStepIcon-completedIcon': {
            color: 'var(--color-shifter)',
            zIndex: 2,                // on top
            fontSize: 30,
            position: 'absolute',     // overlay on circle
            opacity: ownerState.completed ? 1 : 0,
            pointerEvents: ownerState.completed ? 'auto' : 'none',  // ignore clicks when hidden
            transition: 'opacity 0.5s ease-in-out',
            left: '50%',
            top: '50%',
            transform: ownerState.completed
                ? 'translate(-50%, -50%) scale(1)'
                : 'translate(-50%, -50%) scale(0.8)',
            transitionProperty: 'opacity, transform',
        },

        // CIRCLE
        '& .CustomStepperStepIcon-circle': {
            width: 16,
            height: 16,
            borderRadius: '50%',
            backgroundColor: ownerState.active ? 'var(--color-shifter)' : '#BBB',
            transition: 'background-color 0.4s ease-in-out, transform 0.4s ease-in-out, opacity 0.5s ease-in-out',
            transform: ownerState.active ? 'scale(1.2)' : 'scale(1)',
            opacity: ownerState.completed ? 0 : 1,
            pointerEvents: ownerState.completed ? 'none' : 'auto',  // ignore clicks when hidden
        },
    }),
);

function CustomStepperStepIcon(props: StepIconProps) {
    const { active, completed, className } = props;

    return (
        <CustomStepperStepIconRoot ownerState={{ active, completed }} className={className}>
            <Check className="CustomStepperStepIcon-completedIcon" />
            <div className="CustomStepperStepIcon-circle" />
        </CustomStepperStepIconRoot>
    );
}


export { CustomStepperConnector, CustomStepperStepIcon };