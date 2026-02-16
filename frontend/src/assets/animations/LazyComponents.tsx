import { lazy } from 'react';

export const LazySilk = lazy(() => import('./Silk.tsx'));
export const LazyMagicBento = lazy(() => import('./MagicBento.tsx'));
export const LazyLightBeams = lazy(() => import('./SpikeAnimation.tsx').then(mod => ({ default: mod.LightBeams })));