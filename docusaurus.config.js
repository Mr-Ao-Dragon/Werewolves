// @ts-check
// Note: type annotations allow type checking and IDEs autocompletion

const lightCodeTheme = require('prism-react-renderer/themes/github');
const darkCodeTheme = require('prism-react-renderer/themes/dracula');

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'Werewolves',
  tagline: 'Wiki',
  url: 'https://wiki.werewolves.dev',
  baseUrl: '/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/favicon.ico',
  organizationName: 'TeamLapen',
  projectName: 'Werewolves',
  deploymentBranch: 'gh-pages',
  trailingSlash: false,

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
          editUrl: 'https://github.com/TeamLapen/Werewolves/tree/gh-pages-source/',
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      }),
    ],
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      navbar: {
        title: 'Werewolves',
        logo: {
          alt: 'Werewolves Logo',
          src: 'img/logo.svg',
        },
        items: [
          {
            type: 'doc',
            docId: 'wiki/intro',
            position: 'left',
            label: 'Wiki',
          },
          {
            href: 'https://github.com/Teamlapen/Vampirism/wiki',
            label: 'Vampirism Wiki',
            position: 'left'
          },
          {
            href: 'https://www.curseforge.com/minecraft/mc-mods/werewolves-become-a-beast',
            label: 'Curseforge',
            position: 'right'
          },
          {
            href: 'https://github.com/Teamlapen/Werewolves',
            label: 'GitHub',
            position: 'right',
          },
        ],
      },
      footer: {
        style: 'dark',
        links: [
          {
            title: 'Docs',
            items: [
              {
                label: 'Wiki',
                to: '/docs/wiki/intro',
              },
            ],
          },
          {
            title: 'Community',
            items: [
              {
                label: 'GitHub',
                href: 'https://github.com/teamlapen/werewolves',
              },
              {
                label: 'Discord',
                href: 'https://discord.gg/wuamm4P',
              },
              {
                label: 'Twitter',
                href: 'https://twitter.com/TheCheaterpaul',
              },
            ],
          },
        ],
        copyright: `Copyright © ${new Date().getFullYear()} Werewolves, Contributors`,
      },
      prism: {
        theme: lightCodeTheme,
        darkTheme: darkCodeTheme,
      },
      metadata: [
        { name: 'keywords', content: 'minecraft, werewolf, vampirism, forge, wiki' },
        { name: 'twitter:card', content: 'summary' }
      ],
      image: 'img/werewolves-title.png'
    }),
};

module.exports = config;
