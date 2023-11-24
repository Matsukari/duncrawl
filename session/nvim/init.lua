-- local autocmd = vim.api.nvim_create_autocmd

-- Auto resize panes when resizing nvim window
-- autocmd("VimResized", {
--   pattern = "*",
--   command = "tabdo wincmd =",
-- })

-- install packer.nvim if not installed
local execute = vim.api.nvim_command
local fn = vim.fn

local install_path = fn.stdpath('data') .. '/site/pack/packer/start/packer.nvim'

if fn.empty(fn.glob(install_path)) > 0 then
  execute('!git clone https://github.com/wbthomason/packer.nvim ' .. install_path)
  execute 'packadd packer.nvim'
end



local Plug = vim.fn['plug#']
vim.call('plug#begin', '~/.config/nvim/plugged')


Plug("preservim/nerdtree")
Plug("echasnovski/mini.move")
-- Plug('fedepujol/move.nvim')
Plug('neoclide/coc.nvim', {branch =  'release'})
Plug('Pocco81/DAPInstall.nvim')
-- terminal
Plug('akinsho/toggleterm.nvim', {tag = '*'})


-- what about Plug?...
Plug('nvim-lua/plenary.nvim')
Plug('nvim-telescope/telescope.nvim', { tag = '0.1.2' })

Plug('neovim/nvim-lspconfig')
Plug('williamboman/nvim-lsp-installer')
Plug('mfussenegger/nvim-jdtls')
Plug('nvim-treesitter/nvim-treesitter')


vim.call('plug#end')
vim.opt.foldenable = false
vim.opt.foldmethod = "indent"

require("toggleterm").setup()

require('mini.move').setup()
-- Paste from system clipboard
vim.keymap.set('n', '<leader>p', '"+p')  -- paste after cursor
vim.keymap.set('n', '<leader>P', '"+P')  -- paste before cursor


-- local packer = require("packer")
-- packer.startup(function ( )
--   vim.cmd('PlugInstall')
--   vim.cmd('set ft=java')
--   use 'simrat39/symbols-outline.nvim'
--   require("symbols-outline").setup()
-- end)

-- vim.cmd('PlugInstall')
-- vim.cmd('close')
-- vim.cmd('set ft=java')

-- vim.g.sc_server_commands = {java = '/home/ark/Downloads/java-language-server/dist/lang_server_linux.sh'}
-- vim.lsp.start({
--   name = 'java-language-server',
--   cmd = {'/home/ark/Downloads/java-language-server/dist/lang_server_linux.sh'},
--   root_dir = vim.fs.dirname(vim.fs.find({'setup.py', 'pyproject.toml'}, { upward = true })[1]),
-- })
