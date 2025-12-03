// src/components/LocalizedLink.tsx

import { Link, useParams, type LinkProps } from 'react-router-dom';

export const LocalizedLink = ({ to, ...props }: LinkProps) => {
    const { lang } = useParams<{ lang: string }>();

    const destination = to.toString().startsWith('/') ? to : `/${to}`;

    const localizedTo = `/${lang}${destination}`;

    return <Link to={localizedTo} {...props} />;
};
